package com.nissum.techtest.repository;

import com.nissum.techtest.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhoneRepository extends JpaRepository<PhoneEntity, Integer> {
}
