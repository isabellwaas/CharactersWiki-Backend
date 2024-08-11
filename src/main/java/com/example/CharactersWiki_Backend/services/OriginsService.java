package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.Origin;
import com.example.CharactersWiki_Backend.models.Place;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginSummary;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceSummary;
import com.example.CharactersWiki_Backend.repositories.CharactersRepository;
import com.example.CharactersWiki_Backend.repositories.OriginsRepository;
import com.example.CharactersWiki_Backend.repositories.PlacesRepository;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OriginsService implements IOriginsService
{
    private final OriginsRepository originsRepository;
    private final PlacesRepository placesRepository;
    private final CharactersRepository charactersRepository;

    public OriginsService(OriginsRepository originsRepository, PlacesRepository placesRepository, CharactersRepository charactersRepository)
    {
        this.originsRepository = originsRepository;
        this.placesRepository = placesRepository;
        this.charactersRepository = charactersRepository;
    }

    public OriginsResponse getOrigins(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection)
    {
        Sort sort = (sortDirection.isEmpty() || sortDirection.get() == SortDirection.ASC) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Page<OriginSummary> page = originsRepository.findOriginsByNameContaining(query.orElse(""), PageRequest.of(pageNumber-1, perPage, sort));
        return new OriginsResponse(page.getTotalPages(), page.getContent());
    }

    public PlacesResponse getPlaces(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection)
    {
        Sort sort = (sortDirection.isEmpty() || sortDirection.get() == SortDirection.ASC) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Page<PlaceSummary> page = placesRepository.findPlacesByNameContainingOrTypeContaining(query.orElse(""), query.orElse(""), PageRequest.of(pageNumber-1, perPage, sort));
        return new PlacesResponse(page.getTotalPages(), page.getContent());
    }

    public OriginResponse getOriginById(int id) throws NotFoundException
    {
        return originsRepository.findOriginById(id).orElseThrow(() -> new NotFoundException("Origin with id "+id+" not found."));
    }

    public PlaceResponse getPlaceById(int id) throws NotFoundException
    {
        return placesRepository.findPlaceById(id).orElseThrow(() -> new NotFoundException("Place with id "+id+" not found."));
    }

    public IdResponse createOrigin(CreateOrigin createOrigin) throws NotFoundException
    {
        Origin origin = new Origin(createOrigin.name());

        if (createOrigin.description() != null) origin.setDescription(createOrigin.description());

        //TODO: currently no error if id not exists
        if (createOrigin.importantPlacesIds().isPresent())
        {
            List<Place> places=placesRepository.findPlacesByIdIn(createOrigin.importantPlacesIds().get());
            origin.setImportantPlaces(places);
            places.forEach(place -> place.setOrigin(origin));
        }

        if (createOrigin.charactersIds().isPresent())
        {
            List<Character> characters=charactersRepository.findCharactersByIdIn(createOrigin.charactersIds().get());
            origin.setCharacters(characters);
            characters.forEach(character -> character.setOrigin(origin));
        }

        originsRepository.save(origin);
        originsRepository.flush();

        return new IdResponse(origin.getId());
    }

    public IdResponse createPlace(CreatePlace createPlace) throws NotFoundException
    {
        Place place = new Place(createPlace.name(), createPlace.type());

        if (createPlace.originId().isPresent())
            place.setOrigin(originsRepository.findById(createPlace.originId().get()).orElseThrow(() -> new NotFoundException("Origin with id " + createPlace.originId() + " not found.")));

        placesRepository.save(place);
        placesRepository.flush();

        return new IdResponse(place.getId());
    }
}
