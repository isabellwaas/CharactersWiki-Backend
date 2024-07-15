package com.example.CharactersWiki_Backend.models.projectionInterfaces;

import java.util.List;

public interface QuoteResponse
{
    int getId();
    List<QuoteLineSummary> getQuoteLines();
    List<CharacterSummary> getCharacters();
}