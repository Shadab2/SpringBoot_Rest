package com.shadab.expensetrackerapi.services;

import com.shadab.expensetrackerapi.domain.User;
import com.shadab.expensetrackerapi.exception.EtAuthException;

// two use case : register a new user , validate a user
public interface UserService {
    User validate(String email, String password) throws EtAuthException;

    User registerUser(String firstName,String lastName,String email,String password) throws EtAuthException;
}
