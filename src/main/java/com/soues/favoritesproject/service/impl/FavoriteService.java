package com.soues.favoritesproject.service.impl;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.dto.SortBy;
import com.soues.favoritesproject.dto.SortType;
import com.soues.favoritesproject.exception.NotFoundException;
import com.soues.favoritesproject.persistence.entity.Category;
import com.soues.favoritesproject.persistence.entity.Favorite;
import com.soues.favoritesproject.persistence.repository.ICategoryRepository;
import com.soues.favoritesproject.persistence.repository.IFavoriteRepository;
import com.soues.favoritesproject.service.IFavoriteService;
import com.soues.favoritesproject.utils.DTOHelper;
import com.soues.favoritesproject.utils.UtilsFavorites;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * A class that receives the business data and sends it to the persistence layer.
 *
 * Implements the interface IFavoriteService.
 */
@Service
@Transactional
public class FavoriteService implements IFavoriteService  {

    @Autowired
    private IFavoriteRepository favoriteRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private  DTOHelper helper;

    @Override
    public List<FavoriteItem> findAll() {
        return favoriteRepository.findAll().stream()
                .map(helper::toFavoriteItem)
                .toList();
    }

    @Override
    public FavoriteItem findOne(long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pas trouvé"));
        return helper.toFavoriteItem(favorite);
    }

    @Override
    public List<FavoriteItem> findAllByCategory(long id) {
        return favoriteRepository.findAll()
                .stream()
                .map(helper::toFavoriteItem)
                .filter(favorite -> favorite.getCategory().getId().equals(id))
                .toList();
    }

    public List<FavoriteItem> findAllByOrder(SortType sortType, SortBy sortBy) {
        List<Favorite> list;

        if (sortType.equals(SortType.category))
                if (sortBy.equals(SortBy.ASC))
                    list = favoriteRepository.findAllByOrderByCategoryLabelAsc();
                else
                    list = favoriteRepository.findAllByOrderByCategoryLabelDesc();
        else
            if (sortBy.equals(SortBy.ASC))
                list = favoriteRepository.findAllByOrderByDateAsc();
            else
                list = favoriteRepository.findAllByOrderByDateDesc();

        return list
                .stream()
                .map(helper::toFavoriteItem)
                .toList();
    }

    @Override
    public List<FavoriteItem> findAllByOrderByDate(SortBy sortBy) {
        if (sortBy.equals(SortBy.ASC))
            return findAllByOrder(SortType.date, SortBy.ASC);
        else
            return findAllByOrder(SortType.date, SortBy.DESC);
    }

    @Override
    public List<FavoriteItem> findAllByOrderByCategoryLabel(SortBy sortBy) {
        if (sortBy.equals(SortBy.ASC))
            return findAllByOrder(SortType.category, SortBy.ASC);
        else
            return findAllByOrder(SortType.category, SortBy.DESC);
    }

    /**
     * Creates a new favorite.
     * Receives FavoriteDefinition DTO params and uses them to create a FavoriteItem object.
     *
     * @param definition DTO used to get datas
     * @param categoryId category id of the favorite
     * @return FavoriteItem object send to persistence layer and more precisely Favorite entity.
     */
    @Override
    public FavoriteItem save(FavoriteDefinition definition, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Pas trouvé"));

        Favorite favorite;

        if (definition.getId() != null) {
            favorite = favoriteRepository.findById(definition.getId())
                    .orElseThrow(() -> new NotFoundException("Pas trouvé"));
        } else {
            favorite = new Favorite();
        }

        favorite.setCategory(category);
        favorite.setLink(definition.getLink());
        favorite.setLabel(definition.getLabel());
        if(favorite.getDate()==null){
            favorite.setDate(new Date());
        }
        favorite.setValidity(UtilsFavorites.CheckValidityURL(definition.getLink()));

        favorite = favoriteRepository.save(favorite);

        return helper.toFavoriteItem(favorite);
    }

    @Override
    public void delete(long id) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException("Pas trouvé"));
        favoriteRepository.deleteById(favorite.getId());
    }

    @Override
    public void deleteMultiple(List<Long> ids) {
        ids.forEach(this::delete);
    }


}
