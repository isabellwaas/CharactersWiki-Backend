package com.example.CharactersWiki_Backend.models;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="origins")
public class Origin
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name="name", nullable=false)
    @Size(min=1, max=100, message="name must have between {min} and {max} characters.")
    @NotBlank(message="name must not be empty.")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="origin", cascade = CascadeType.ALL)
    private List<Place> importantPlaces;

    @Column(name="description", nullable=true)
    @Size(max=500, message="description must not be longer than {max} characters.")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="origin", cascade = CascadeType.ALL)
    private List<Character> characters;
}
