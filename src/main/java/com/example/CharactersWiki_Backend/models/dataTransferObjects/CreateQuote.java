package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Optional;

public record CreateQuote(
        List<CreateQuoteLine> quoteLines,
        @Nullable List<Integer> charactersIds
) {}
