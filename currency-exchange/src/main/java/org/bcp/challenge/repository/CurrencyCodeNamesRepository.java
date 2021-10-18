package org.bcp.challenge.repository;

import org.bcp.challenge.repository.entity.CurrencyCodeNames;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyCodeNamesRepository extends CrudRepository<CurrencyCodeNames, Long> {


    @Query("FROM CurrencyCodeNames cn WHERE cn.state= true")
    List<CurrencyCodeNames> findAll();
}
