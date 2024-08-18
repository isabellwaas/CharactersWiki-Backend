package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;

import java.util.List;

public record UpdateQuote(
        @Nullable List<CreateQuoteLine> quoteLines,
        @Nullable List<Integer> charactersIds
) {}