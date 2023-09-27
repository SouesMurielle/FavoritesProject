package com.soues.favoritesproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Class used to get CategoryIem from database (id can't be null)
@Data
// raccourci qui permet de générer automatiquement une classe compatible POJO
// génère les getter et setter ainsi que le NoArgsConstructor
@AllArgsConstructor
// génère un constructeur avec tous les arguments en paramètres
// force l'utilisation de ce constructeur pour instancier un objet de ce type
// il faut donc ajouter @NoArgsConstructor afin de rajouter un constructeur sans paramètres (utile lors de la compilation notamment)
@NoArgsConstructor
public class CategoryItem {
    private long id;
    private String label;
}
