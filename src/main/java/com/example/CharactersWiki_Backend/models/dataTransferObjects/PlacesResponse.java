package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceSummary;

import java.util.List;

public record PlacesResponse(
        int maximumPage,
        List<PlaceSummary> places
) {}

