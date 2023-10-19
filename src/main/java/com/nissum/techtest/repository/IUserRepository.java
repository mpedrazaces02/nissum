package com.nissum.techtest.repository;

import com.nissum.techtest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(String email);

}
