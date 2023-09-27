package com.soues.favoritesproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Class used to get CategoryIem from database (id can't be null)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListItem {
    private long id;
    private String label;
    @Schema(name = "references", description = "The number of usages of this category")
    private long references;
}
