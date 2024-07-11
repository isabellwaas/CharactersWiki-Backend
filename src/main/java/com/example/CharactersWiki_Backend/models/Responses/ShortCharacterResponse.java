package com.example.CharactersWiki_Backend.models.Responses;

public record ShortCharacterResponse(
        int id,
        String title,
        String firstname,
        String lastname,
        String nickname
){}
