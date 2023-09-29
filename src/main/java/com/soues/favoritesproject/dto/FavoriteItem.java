package com.soues.favoritesproject.dto;

import com.soues.favoritesproject.persistence.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object used for transferring data between the controller and the services.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteItem {

    private Long id;
    private String label;
    private String link;
    private Date date;
    private Category category;
    private boolean validity;

}
