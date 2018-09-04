package com.neo.repository;

import com.neo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findById(String id);

    Long deleteById(String id);

    User findByUserName(String userName);
}