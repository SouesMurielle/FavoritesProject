package com.soues.favoritesproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Class used to get CategoryIem from database
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItem {
    private long id;
    private String label;
}
