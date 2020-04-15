package org.bcp.challenge.currencyexchange.repository;

import org.bcp.challenge.currencyexchange.repository.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String login);
}
