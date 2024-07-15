package com.example.CharactersWiki_Backend.models.projectionInterfaces;

import java.util.List;

public interface WeaponResponse
{
    int getId();
    String getName();
    List<CharacterSummary> getCharacters();
}