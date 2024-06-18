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
@Table(name="quotes")
public class Quote
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    /*@Column(name="quoteLines", nullable=true)
    private List<QuoteLine> quoteLines;*/

    /*@Column(name="characters", nullable=true)
    private List<Character> characters;*/
}
