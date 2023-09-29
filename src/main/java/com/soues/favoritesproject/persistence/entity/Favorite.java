package com.soues.favoritesproject.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Entity that represents a favorite.
 * Use IFavoriteRepository.
 * Retrieve data from the service and store it in the database using the JPA framework.
 * Allows to create and update favorite table in the database.
 * It contains datas like id, label, link, date, category and validity.
 */
@Entity
@Table(name = "favorite", uniqueConstraints = {@UniqueConstraint(columnNames = {"link"})})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    /**
     * Represents id column from database in favorite table.
     * This param is unique, not nullable and a primary key.
     * It is also auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "label", length=100)
    private String label;

    /**
     * As some navigators can handle very long urls (80 000), this column
     * represents an url and can reach a length of 65535 characters
     */
    @Column(name = "link", length=65535)
    private String link;

    @Column(name = "update_at", columnDefinition = "date")
    private Date date;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Category category;

    @Column(name = "validity", nullable = false)
    private Boolean validity;

}
