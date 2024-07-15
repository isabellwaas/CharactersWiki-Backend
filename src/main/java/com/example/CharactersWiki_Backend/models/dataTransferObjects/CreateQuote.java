package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import java.util.List;
import java.util.Optional;

public record CreateQuote(
        List<CreateQuoteLine> quoteLines,
        Optional<List<Integer>> charactersIds
) {}
