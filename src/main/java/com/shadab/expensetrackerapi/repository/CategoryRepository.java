package com.shadab.expensetrackerapi.repository;

import com.shadab.expensetrackerapi.domain.Category;
import com.shadab.expensetrackerapi.exception.EtBadRequestException;
import com.shadab.expensetrackerapi.exception.EtResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId) throws EtResourceNotFoundException;
    Category findById(Integer userId,Integer categoryId) throws EtBadRequestException;
    Integer create(Integer userId,String title,String descpription) throws EtBadRequestException;
    void update (Integer userId,Integer categoryId,Category category) throws EtBadRequestException;
    void removeById(Integer userId,Integer categoryId);
}
