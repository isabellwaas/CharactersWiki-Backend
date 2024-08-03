package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceSummary;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterSummary;

import java.util.List;

public record AllegiancesResponse(
        int maximumPage,
        List<AllegianceSummary> allegiances
){}
