package com.example.CharactersWiki_Backend.models.projectionInterfaces;

import java.util.List;

public interface OriginResponse
{
    int getId();
    String getName();
    List<PlaceSummary> getImportantPlaces();
    String getDescription();
    List<CharacterSummary> getCharacters();
}
