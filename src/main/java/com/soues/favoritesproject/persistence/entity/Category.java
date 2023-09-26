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

    @Column(name = "label")
    private String label;

}
