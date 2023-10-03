package com.soues.favoritesproject.service;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.dto.SortBy;

import java.util.List;

public interface IFavoriteService {

    List<FavoriteItem> findAll();

    FavoriteItem findOne(long id);

    List<FavoriteItem> findAllByCategory(long id);

    List<FavoriteItem> findAllByOrderByDate(SortBy sortBy);

    List<FavoriteItem> findAllByOrderByCategoryLabel(SortBy sortBy);

    FavoriteItem save(FavoriteDefinition favorite, Long categoryId);

    FavoriteItem saveRobot(FavoriteDefinition definition, Long categoryId);

    void delete(long id);

    void deleteMultiple(List<Long> ids);

}
