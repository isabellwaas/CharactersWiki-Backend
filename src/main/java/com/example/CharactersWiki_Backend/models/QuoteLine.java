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

    @NonNull
    @Column(name="characterName", nullable=false)
    @Size(min=1, max=300, message="characterName must have between {min} and {max} characters.")
    @NotBlank(message="characterName must not be empty.")
    private String characterName;

    @NonNull
    @Column(name="text", nullable=false)
    @Size(min=1, max=300, message="text must have between {min} and {max} characters.")
    @NotBlank(message="text must not be empty.")
    private String text;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="quote_id")
    private Quote quote;
}