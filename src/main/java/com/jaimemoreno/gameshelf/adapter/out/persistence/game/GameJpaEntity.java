package com.jaimemoreno.gameshelf.adapter.out.persistence.game;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class GameJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_image_url", length = 1024)
    private String coverImageUrl;

    @Column(name = "release_date", length = 32)
    private String releaseDate;

    GameJpaEntity(Long id, String title, String description, String coverImageUrl, String releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.releaseDate = releaseDate;
    }
}
