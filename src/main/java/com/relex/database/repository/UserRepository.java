package com.relex.database.repository;


import com.relex.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByNickname(String name);
    User findUserByMail(String mail);
    Optional<User> findByMail(String mail);
    void deleteUserByMail(String mail);
    Boolean existsByName(String name);
    Boolean existsByMail(String mail);
}
