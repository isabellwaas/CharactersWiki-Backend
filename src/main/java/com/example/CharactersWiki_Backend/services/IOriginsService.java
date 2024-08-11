package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceResponse;
import com.example.CharactersWiki_Backend.utilities.SortDirection;

import java.util.Optional;

public interface IOriginsService
{
    public OriginsResponse getOrigins(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection);
    public PlacesResponse getPlaces(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection);
    public OriginResponse getOriginById(int id) throws NotFoundException;
    public PlaceResponse getPlaceById(int id) throws NotFoundException;
    public IdResponse createOrigin(CreateOrigin createOrigin) throws NotFoundException;
    public IdResponse createPlace(CreatePlace createPlace) throws NotFoundException;
}
