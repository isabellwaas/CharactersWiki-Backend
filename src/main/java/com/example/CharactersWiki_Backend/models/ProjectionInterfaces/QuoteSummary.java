package com.example.CharactersWiki_Backend.models.projectionInterfaces;

import java.util.List;

public interface QuoteSummary
{
    int getId();
    List<QuoteLineSummary> getQuoteLines();
}
