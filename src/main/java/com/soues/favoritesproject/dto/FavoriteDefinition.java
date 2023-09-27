package com.soues.favoritesproject.dto;

import com.soues.favoritesproject.persistence.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDefinition {
    private Long id;
    private String label;
    private String link;
}
