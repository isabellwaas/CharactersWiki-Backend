package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceSummary;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponSummary;

import java.util.List;

public record WeaponsResponse(
        int maximumPage,
        List<WeaponSummary> weapons
){}
