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
@Table(name="quoteLines")
public class QuoteLine
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    /*@Column(name="character", nullable=true)
    private Character character;*/

    @NonNull
    @Column(name="text", nullable=false)
    @Size(min=1, max=300, message="text must have between {min} and {max} characters.")
    @NotBlank(message="text must not be empty.")
    private String text;

    /*@Column(name="quote", nullable=true)
    private Quote quote;*/
}
