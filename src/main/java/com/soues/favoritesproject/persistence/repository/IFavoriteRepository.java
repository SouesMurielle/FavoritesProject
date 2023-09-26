package com.soues.favoritesproject.persistence.repository;

import com.soues.favoritesproject.persistence.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {

//    @Query(value = "select u from favorites f where f.category_id = ?1",nativeQuery = true)
//    List<Favorite> findByCategory(long id);

    List<Favorite> findAllByOrderByDateAsc();
    List<Favorite> findAllByOrderByDateDesc();
//    List<Favorite> findAllByOrderByCategoryNameAsc();
//    List<Favorite> findAllByOrderByCategoryNameDesc();
//    List<Favorite> findByCategoryIdOrderByUpdatedAtDesc(Long categoryId);
//    List<Favorite> findByCategoryIdOrderByUpdatedAtAsc(Long categoryId);
//    List<Favorite> findByCategoryIdOrderByCategoryNameDesc(Long categoryId);
//    List<Favorite> findByCategoryIdOrderByCategoryNameAsc(Long categoryId);
//    List<Favorite> findByCategoryId(Long categoryId);
}
