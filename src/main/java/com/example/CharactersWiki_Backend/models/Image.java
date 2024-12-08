package com.example.CharactersWiki_Backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Data //Lombok: Generates Getters, Setters, toString, equals, and hashCode methods
@NoArgsConstructor
@Entity
@Table(name="images")
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "fileNameFullQuality", nullable = false)
    private String fileNameFullQuality;

    @NonNull
    @Column(name = "fileNamePreviewQuality", nullable = false)
    private String fileNamePreviewQuality;

    @NonNull
    @Column(name = "originalname", nullable = false)
    @Size(min = 1, max = 100, message = "originalname must have between {min} and {max} characters.")
    @NotBlank(message = "originalname must not be empty.")
    private String originalname;

    @NonNull
    @Column(name = "widthFullQuality", nullable = false)
    private int widthFullQuality;

    @NonNull
    @Column(name = "heightFullQuality", nullable = false)
    private int heightFullQuality;

    @NonNull
    @Column(name = "widthPreviewQuality", nullable = false)
    private int widthPreviewQuality;

    @NonNull
    @Column(name = "heightPreviewQuality", nullable = false)
    private int heightPreviewQuality;

    @OneToOne(mappedBy = "image")
    private Character character;

    public Image(UUID uuidFileFullQuality, UUID uuidFilePreviewQuality, String fileEnding, String originalname, int widthFullQuality, int heightFullQuality, int widthPreviewQuality, int heightPreviewQuality)
    {
        this.fileNameFullQuality = uuidFileFullQuality + fileEnding;
        this.fileNamePreviewQuality = uuidFilePreviewQuality + fileEnding;
        this.originalname = originalname;
        this.widthFullQuality = widthFullQuality;
        this.heightFullQuality = heightFullQuality;
        this.widthPreviewQuality = widthPreviewQuality;
        this.heightPreviewQuality = heightPreviewQuality;
    }
}
