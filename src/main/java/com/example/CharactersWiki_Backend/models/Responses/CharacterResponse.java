package com.example.CharactersWiki_Backend.models.Responses;

import java.util.List;

public record CharacterResponse(
    int id,
    String title,
    String firstname,
    String lastname,
    String nickname,
    int age,
    String species,
    String status,
    ShortOriginResponse origin,
    String hair,
    String eyes,
    String skin,
    String gender,
    List<ShortAllegianceResponse> allegiances,
    List<ShortCharacterResponse> family,
    List<ShortCharacterResponse> children,
    List<ShortCharacterResponse> loveInterests,
    List<ShortCharacterResponse> allies,
    List<ShortCharacterResponse> enemies,
    List<ShortWeaponResponse> weapons,
    List<ShortQuoteResponse> quotes,
    String biography
){}