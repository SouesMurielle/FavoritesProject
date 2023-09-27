package com.soues.favoritesproject.persistence.repository;

import com.soues.favoritesproject.persistence.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findAllByOrderByDateAsc();
    List<Favorite> findAllByOrderByDateDesc();
    List<Favorite> findAllByOrderByCategoryLabelAsc();
    List<Favorite> findAllByOrderByCategoryLabelDesc();
}
