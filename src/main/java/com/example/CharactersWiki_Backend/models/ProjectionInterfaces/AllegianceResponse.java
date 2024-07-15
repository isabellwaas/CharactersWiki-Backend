package com.example.CharactersWiki_Backend.models.projectionInterfaces;

import java.util.List;

public interface AllegianceResponse
{
    int getId();
    String getName();
    String getNote();
    List<CharacterSummary> getCharacters();
}