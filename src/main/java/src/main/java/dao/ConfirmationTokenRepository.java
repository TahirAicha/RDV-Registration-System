package src.main.java.dao;

import org.springframework.data.repository.CrudRepository;

import src.main.java.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}