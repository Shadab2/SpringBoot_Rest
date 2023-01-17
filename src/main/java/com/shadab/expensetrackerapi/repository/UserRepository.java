package com.shadab.expensetrackerapi.repository;


import com.shadab.expensetrackerapi.domain.User;
import com.shadab.expensetrackerapi.exception.EtAuthException;

// defines dB operations in here
public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByEmailAndPassword(String email,String password) throws  EtAuthException;

    Integer getCountByEmail(String email);

    User findById(Integer id);
}
