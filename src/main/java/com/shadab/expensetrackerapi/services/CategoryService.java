package com.shadab.expensetrackerapi.services;

import com.shadab.expensetrackerapi.domain.Category;
import com.shadab.expensetrackerapi.exception.EtBadRequestException;
import com.shadab.expensetrackerapi.exception.EtResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategories(Integer userId);
    Category fetchCategoryById(Integer userId, Integer categoryId) throws EtResourceNotFoundException;
    Category addCategory(Integer userId,String title,String description) throws EtBadRequestException;
    void updateCategory(Integer userId,Integer categoryId,Category category) throws EtBadRequestException;
    // we  also have to delete all associated trasaction with the given category , this is known Cascade delete
    void removeCategoryWithAllTransaction(Integer userId,Integer categoryId) throws EtResourceNotFoundException;
}
