package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterSummary;

import java.util.List;

public record CharactersResponse(
        int maximumPage,
        List<CharacterSummary> characters
){}
