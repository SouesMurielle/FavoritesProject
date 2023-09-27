package com.soues.favoritesproject.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "favorite", uniqueConstraints = {@UniqueConstraint(columnNames = {"link"})})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "label", length=100)
    private String label;

    @Column(name = "link")
    private String link;

    @Column(name = "Update_at", columnDefinition = "date")
    private Date date;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Category category;

    @Column(name = "validity", nullable = false)
    private Boolean validity;

}
