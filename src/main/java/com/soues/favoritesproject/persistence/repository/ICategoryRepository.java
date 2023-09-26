package com.soues.favoritesproject.persistence.repository;

import com.soues.favoritesproject.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
