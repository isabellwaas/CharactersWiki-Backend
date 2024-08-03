package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;

import java.util.List;

public record QuotesResponse(
        int maximumPage,
        List<QuoteResponse> quotes
){}
