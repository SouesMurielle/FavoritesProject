package com.soues.favoritesproject.service;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.dto.SortBy;

import java.util.List;

// pas d'annotations car c'est une classe que nous faisons nous même, ce n'est pas géré par Spring
public interface IFavoriteService {

    List<FavoriteItem> findAll();

    FavoriteItem findOne(long id);

    List<FavoriteItem> findAllByCategory(long id);

    List<FavoriteItem> findAllByOrderByDate(SortBy sortBy);

    List<FavoriteItem> findAllByOrderByCategoryLabel(SortBy sortBy);

    FavoriteItem save(FavoriteDefinition favorite, Long categoryId, boolean isValid);

    void delete(long id);

    void deleteMultiple(List<Long> ids);

}
