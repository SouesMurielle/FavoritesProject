package com.soues.favoritesproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Class used to define Category object (id can be null)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDefinition {
    private Long id;
    private String label;
}
