package com.example.CharactersWiki_Backend.models.ProjectionInterfaces;

import java.util.List;

public interface CharacterResponse
{
    int getId();
    String getFirstname();
    String getLastname();
    String getNickname();
    int getAge();
    String getSpecies();
    String getStatus();
    OriginSummary getOrigin();
    String getHair();
    String getEyes();
    String getSkin();
    String getGender();
    List<AllegianceSummary> getAllegiances();
    List<CharacterSummary> getFamily();
    List<CharacterSummary> getChildren();
    List<CharacterSummary> getLoveInterests();
    List<CharacterSummary> getAllies();
    List<CharacterSummary> getEnemies();
    List<WeaponSummary> getWeapons();
    List<QuoteSummary> getQuotes();
    String getBiography();
}
