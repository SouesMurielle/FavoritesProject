package com.soues.favoritesproject.persistence.repository;

import com.soues.favoritesproject.persistence.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query(value = "select u from favorites f where f.category_id = ?1",nativeQuery = true)
    List<Favorite> findByCategory(long id);

}
