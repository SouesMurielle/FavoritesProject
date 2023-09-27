package com.soues.favoritesproject.service;

import com.soues.favoritesproject.dto.CategoryDefinition;
import com.soues.favoritesproject.dto.CategoryListItem;

import java.util.List;

public interface ICategoryService {

    List<CategoryListItem> findAll();

    void delete(long id);

    CategoryListItem save(CategoryDefinition category);
}
