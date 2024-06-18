package com.example.CharactersWiki_Backend.models;

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
@Table(name="places")
public class Place
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name="name", nullable=false)
    @Size(min=1, max=100, message="name must have between {min} and {max} characters.")
    @NotBlank(message="name must not be empty.")
    private String name;

    @NonNull
    @Column(name="type", nullable=false)
    @Size(min=1, max=100, message="type must have between {min} and {max} characters.")
    @NotBlank(message="type must not be empty.")
    private String type;

    /*@Column(name="origin", nullable=true)
    private Origin origin;*/
}
