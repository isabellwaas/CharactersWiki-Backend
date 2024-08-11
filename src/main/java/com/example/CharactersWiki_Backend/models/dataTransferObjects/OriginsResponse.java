package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginSummary;

import java.util.List;

public record OriginsResponse(
    int maximumPage,
    List<OriginSummary> origins
) {}
