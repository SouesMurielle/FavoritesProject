package com.soues.favoritesproject.service.impl;

import com.soues.favoritesproject.dto.CategoryDefinition;
import com.soues.favoritesproject.dto.CategoryListItem;
import com.soues.favoritesproject.exception.NotFoundException;
import com.soues.favoritesproject.persistence.entity.Category;
import com.soues.favoritesproject.persistence.repository.ICategoryRepository;
import com.soues.favoritesproject.service.ICategoryService;
import com.soues.favoritesproject.utils.DTOHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private DTOHelper helper;

    @Override
    public List<CategoryListItem> findAll() {
        return categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category::getLabel))
                .map(helper::toCategoryToListItem)
                .toList();
    }

    @Override
    public void delete(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Pas trouvé"));
        categoryRepository.deleteById(category.getId());
    }

    @Override
    public CategoryListItem save(CategoryDefinition definition) {

        Category category;

        if (definition.getId() != null) {
            category = categoryRepository.findById(definition.getId())
                    .orElseThrow(() -> new NotFoundException("Pas trouvé"));
        } else {
            category = new Category();
        }

        category.setLabel(definition.getLabel());

        category = categoryRepository.save(category);

        return helper.toCategoryToListItem(category);
    }
}
