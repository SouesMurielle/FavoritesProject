package com.soues.favoritesproject.service;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.dto.SortParam;

import java.util.List;

// pas d'annotations car c'est une classe que nous faisons nous même, ce n'est pas géré par Spring
public interface IFavoriteService {

    List<FavoriteItem> findAll();

//    List<FavoriteItem> findAll(ItemSortBy sortBy, ItemSortType sortType, Long categoryId);

    FavoriteItem findOne(long id);

    List<FavoriteItem> findByCategory(long id);

    List<FavoriteItem> findAllByOrderByDate(SortParam sortParam);

    List<FavoriteItem> findAllByOrderByCategoryLabel(SortParam sortParam);

    FavoriteItem save(FavoriteDefinition favorite, Long categoryId);

    void delete(long id);

    void deleteMultiple(List<Long> ids);

}
