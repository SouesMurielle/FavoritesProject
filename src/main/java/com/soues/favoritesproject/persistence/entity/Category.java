package com.soues.favoritesproject.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(columnNames = {"label"})})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

//    unique = true > vérifie coté mySQL que le champ label soit unique
//    il serait plus "correct" de le faire en java dans le controller en
//    important la liste des catégories avant d'en créer une
//    ça sert notamment à renvoyer une exception java et pas mysql (grave mieux)
    @Column(name = "label")
    private String label;

}
