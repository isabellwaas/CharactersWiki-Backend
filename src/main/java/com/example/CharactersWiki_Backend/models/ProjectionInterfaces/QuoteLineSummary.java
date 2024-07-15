package com.example.CharactersWiki_Backend.models.projectionInterfaces;


public interface QuoteLineSummary
{
    int getId();
    CharacterSummary getCharacter();
    String getText();
}