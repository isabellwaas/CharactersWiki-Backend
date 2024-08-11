package com.example.CharactersWiki_Backend.models.projectionInterfaces;

public interface PlaceResponse
{
    int getId();
    String getName();
    String getType();
    OriginSummary getOrigin();
}
