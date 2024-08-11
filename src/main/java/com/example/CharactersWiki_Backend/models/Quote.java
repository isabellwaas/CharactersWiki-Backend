package com.example.CharactersWiki_Backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="quotes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Character> characters;
}