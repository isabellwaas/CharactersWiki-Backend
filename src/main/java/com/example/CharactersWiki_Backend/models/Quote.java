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
//@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name="quotes")
public class Quote
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quote", cascade = CascadeType.ALL)
    private List<QuoteLine> quoteLines;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="quotes", cascade = CascadeType.ALL)
    private List<Character> characters;
}
