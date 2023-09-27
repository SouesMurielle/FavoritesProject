package com.soues.favoritesproject.persistence.repository;

import com.soues.favoritesproject.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}

// On a besoin de récupérer les categories donc on fait un repository des categories
