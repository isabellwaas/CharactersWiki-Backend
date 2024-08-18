package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;

import java.util.List;

public abstract class CharacterRelationships
{
    @Nullable public Integer originId;
    @Nullable public List<Integer> allegiancesIds;
    @Nullable public List<Integer> familyIds;
    @Nullable public List<Integer> childrenIds;
    @Nullable public List<Integer> loveInterestsIds;
    @Nullable public List<Integer> alliesIds;
    @Nullable public List<Integer> enemiesIds;
    @Nullable public List<Integer> weaponsIds;
    @Nullable public List<Integer> quotesIds;
}