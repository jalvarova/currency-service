package org.jalvarova.currency;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class TestBasic {

    @BeforeAll
    public static void init() {
        log.info("Congrats");
    }

    @DisplayName("Test basic")
    @Test
    void testApplyCurrencyExchange() {
        int number = 30;
        int number2 = 30;

        Assertions.assertEquals(number + number2, 60);
        Assertions.assertEquals(number - number2, 0);

    }
}
