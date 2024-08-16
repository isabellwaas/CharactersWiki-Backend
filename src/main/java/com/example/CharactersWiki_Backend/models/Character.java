package com.example.CharactersWiki_Backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data //Lombok: Generates Getters, Setters, toString, equals, and hashCode methods
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="characters")
public class Character
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="title", nullable=true)
    @Size(max=100, message="title must not be longer than {max} characters.")
    private String title;

    @NonNull
    @Column(name="firstname", nullable=false)
    @Size(min=1, max=100, message="firstname must have between {min} and {max} characters.")
    @NotBlank(message="firstname must not be empty.")
    private String firstname;

    @Column(name="lastname", nullable=true)
    @Size(max=100, message="lastname must not be longer than {max} characters.")
    private String lastname;

    @Column(name="nickname", nullable=true)
    @Size(max=100, message="nickname must not be longer than {max} characters.")
    private String nickname;

    @NonNull
    @Column(name="age", nullable=false)
    private int age;

    @NonNull
    @Column(name="species", nullable=false)
    @Size(min=1, max=50, message="species must have between {min} and {max} characters.")
    @NotBlank(message="species must not be empty.")
    private String species;

    @NonNull
    @Column(name="status", nullable=false)
    @Size(min=1, max=30, message="status must have between {min} and {max} characters.")
    @NotBlank(message="status must not be empty.")
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="origin_id")
    private Origin origin;

    @NonNull
    @Column(name="hair", nullable=false)
    @Size(min=1, max=30, message="hair must have between {min} and {max} characters.")
    @NotBlank(message="hair must not be empty.")
    private String hair;

    @NonNull
    @Column(name="eyes", nullable=false)
    @Size(min=1, max=30, message="eyes must have between {min} and {max} characters.")
    @NotBlank(message="eyes must not be empty.")
    private String eyes;

    @NonNull
    @Column(name="skin", nullable=false)
    @Size(min=1, max=30, message="skin must have between {min} and {max} characters.")
    @NotBlank(message="skin must not be empty.")
    private String skin;

    @NonNull
    @Column(name="gender", nullable=false)
    @Size(min=1, max=50, message="gender must have between {min} and {max} characters.")
    @NotBlank(message="gender must not be empty.")
    private String gender;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="characters_allegiances", joinColumns = @JoinColumn(name="character_id"), inverseJoinColumns = @JoinColumn(name="allegiance_id"))
    private List<Allegiance> allegiances;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Character> family;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Character> children;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Character> loveInterests;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Character> allies;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Character> enemies;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="characters_weapons", joinColumns = @JoinColumn(name="character_id"), inverseJoinColumns = @JoinColumn(name="weapon_id"))
    private List<Weapon> weapons;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="characters_quotes", joinColumns = @JoinColumn(name="character_id"), inverseJoinColumns = @JoinColumn(name="quote_id"))
    private List<Quote> quotes;

    @Column(name="biography", nullable=true)
    @Size(max=500, message="biography must not be longer than {max} characters.")
    private String biography;
}
