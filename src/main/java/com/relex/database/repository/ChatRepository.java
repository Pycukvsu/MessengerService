package com.relex.database.repository;

import com.relex.messenger.models.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends CrudRepository<Chat, Long> {
    Chat findByFirstUsernameAndSecondUsername(String firstNickname, String secondUsername);

}
