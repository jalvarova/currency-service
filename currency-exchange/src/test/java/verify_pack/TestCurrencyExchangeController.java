package verify_pack;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.jalvarova.currency.controller.CurrencyExchangeController;
import org.jalvarova.currency.controller.UserController;
import org.jalvarova.currency.dto.CurrencyExchangeDto;
import org.jalvarova.currency.dto.CurrencyExchangeRsDto;
import org.jalvarova.currency.dto.JwtRequest;
import org.jalvarova.currency.dto.JwtResponse;
import org.jalvarova.currency.dto.enums.CurrencyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestCurrencyExchangeController {

    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeController currencyExchangeController;

    @MockBean
    private UserController userController;

    @Autowired
    private ApplicationContext applicationContext;

    private static JwtRequest jwtRequest = new JwtRequest();

    private static String authToken;
    private static String authTokenType;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    @WithMockUser(username = "walavo", password = "12334")
    public void init() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        authToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YWxhdm8iLCJleHAiOjE2MzI4OTc4MTYsImlhdCI6MTYzMjg3OTgxNn0.pZdh-SvzLHmMZkshC2v5MM7pY1UgdUoeXNoBjMjCmv9rfYjiMYJ1tIw4JXJLbYzAHUhJuCCfSh3Kcwqjb_s3RA";
        authTokenType = "Bearer ";
        jwtRequest.setUsername("walavo");
        jwtRequest.setPassword("12334");

        BDDMockito.given(userController.login(jwtRequest)).willReturn(
                JwtResponse
                        .builder()
                        .tokenType(authTokenType)
                        .token(authToken)
                        .build()
        );
        mockMvc
                .perform(
                        post("/authentication")
                                .content(objectMapper.writeValueAsBytes(jwtRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> log.info("Response" + mvcResult.getResponse().getStatus()));
    }

    @DisplayName("Test apply currency exchange")
    @Test
    void testApplyCurrencyExchange() throws Exception {
        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;

        BigDecimal amount = BigDecimal.valueOf(5);
        BigDecimal exchangeRate = BigDecimal.valueOf(3.39608);

        CurrencyExchangeDto currencyExchangeDto =
                CurrencyExchangeDto
                        .builder()
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .amount(amount)
                        .build();

        CurrencyExchangeRsDto currencyExchangeRsDto = CurrencyExchangeRsDto
                .builder()
                .amount(exchangeRate)
                .currencyOrigin(currencyUSD)
                .currencyDestination(currencyPEN)
                .exchangeRateAmount(BigDecimal.valueOf(16.98040))
                .exchangeRate(exchangeRate)
                .build();

        BDDMockito
                .given(currencyExchangeController.getCurrencyExchange(currencyExchangeDto))
                .willReturn(Single.just(currencyExchangeRsDto));

        mockMvc
                .perform(
                        post("/api/v1/currency-exchange/apply")
                                .header("Authorization", authTokenType + authToken)
                                .content(objectMapper.writeValueAsBytes(currencyExchangeDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Test update currency exchange")
    @Test
    void testUpdateCurrencyExchange() throws Exception {
        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;

        BigDecimal amount = BigDecimal.valueOf(5);
        BigDecimal exchangeRate = BigDecimal.valueOf(3.39608);

        CurrencyExchangeDto currencyExchangeDto =
                CurrencyExchangeDto
                        .builder()
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .amount(amount)
                        .build();

        BDDMockito
                .given(currencyExchangeController.updateCurrencyExchange(currencyExchangeDto))
                .willReturn(Single.just(currencyExchangeDto));

        mockMvc
                .perform(
                        put("/api/v1/currency-exchange")
                                .header("Authorization", authTokenType + authToken)
                                .content(objectMapper.writeValueAsBytes(currencyExchangeDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @DisplayName("Test save all currency exchange")
    @Test
    void testSaveCurrencyExchange() throws Exception {
        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        CurrencyCode currencyEUR = CurrencyCode.EUR;

        List<CurrencyExchangeDto> exchanges = Arrays.asList(
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(3.6))
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(10.0))
                        .currencyOrigin(currencyPEN)
                        .currencyDestination(currencyUSD)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(4.5))
                        .currencyOrigin(currencyEUR)
                        .currencyDestination(currencyPEN)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(0.99))
                        .currencyOrigin(currencyPEN)
                        .currencyDestination(currencyEUR)
                        .build());


        BDDMockito.given(currencyExchangeController.saveCurrencyExchange(exchanges)).willReturn(Observable.fromIterable(exchanges));

        mockMvc
                .perform(
                        post("/api/v1/currency-exchange")
                                .header("Authorization", authTokenType + authToken)
                                .content(objectMapper.writeValueAsBytes(exchanges))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @DisplayName("Test get all currency exchange")
    @Test
    void testGetAllCurrencyExchange() throws Exception {

        CurrencyCode currencyUSD = CurrencyCode.USD;
        CurrencyCode currencyPEN = CurrencyCode.PEN;
        CurrencyCode currencyEUR = CurrencyCode.EUR;

        List<CurrencyExchangeDto> exchanges = Arrays.asList(
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(3.39608))
                        .currencyOrigin(currencyUSD)
                        .currencyDestination(currencyPEN)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(0.294475))
                        .currencyOrigin(currencyPEN)
                        .currencyDestination(currencyUSD)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(3.72884))
                        .currencyOrigin(currencyEUR)
                        .currencyDestination(currencyPEN)
                        .build(),
                CurrencyExchangeDto
                        .builder()
                        .amount(BigDecimal.valueOf(0.268180))
                        .currencyOrigin(currencyPEN)
                        .currencyDestination(currencyEUR)
                        .build());


        BDDMockito.given(currencyExchangeController.getAllCurrencyExchange()).willReturn(Single.just(exchanges));

        mockMvc
                .perform(
                        get("/api/v1/currency-exchange")
                                .header("Authorization", authTokenType + authToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
