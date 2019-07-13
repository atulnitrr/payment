package com.springboot.payment.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.springboot.payment.payment.entity.UserEntity;


@Repository
public interface UserRespository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(final String email);
}
