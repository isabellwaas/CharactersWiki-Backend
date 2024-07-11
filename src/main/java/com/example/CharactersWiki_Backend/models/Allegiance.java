package com.example.CharactersWiki_Backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="allegiances")
public class Allegiance
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name="name", nullable=false)
    @Size(min=1, max=100, message="name must have between {min} and {max} characters.")
    @NotBlank(message="name must not be empty.")
    private String name;

    @Column(name="note", nullable=true)
    @Size(max=100, message="note must not be longer than {max} characters.")
    private String note;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="allegiances", cascade = CascadeType.ALL)
    private List<Character> characters;
}
