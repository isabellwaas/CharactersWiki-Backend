package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceResponse;
import com.example.CharactersWiki_Backend.services.IOriginsService;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/origins")
@Validated
public class OriginsController
{
    private final IOriginsService originsService;

    @Autowired
    public OriginsController(IOriginsService originsService)
    {
        this.originsService = originsService;
    }

    @GetMapping("")
    public ResponseEntity<OriginsResponse> getOrigins(@RequestParam Optional<String> query, @RequestParam int pageNumber, @RequestParam int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(originsService.getOrigins(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/places")
    public ResponseEntity<PlacesResponse> getPlaces(@RequestParam Optional<String> query, @RequestParam int pageNumber, @RequestParam int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(originsService.getPlaces(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OriginResponse> getOrigin(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(originsService.getOriginById(id));
    }

    @GetMapping("/places/{id}")
    public ResponseEntity<PlaceResponse> getPlace(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(originsService.getPlaceById(id));
    }

    @PostMapping("")
    public ResponseEntity<IdResponse> createOrigin(@RequestBody @Valid CreateOrigin createOrigin) throws NotFoundException
    {
        return ResponseEntity.status(201).body(originsService.createOrigin(createOrigin));
    }

    @PostMapping("/places")
    public ResponseEntity<IdResponse> createPlace(@RequestBody @Valid CreatePlace createPlace) throws NotFoundException
    {
        return ResponseEntity.status(201).body(originsService.createPlace(createPlace));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateOrigin(@PathVariable int id, @RequestBody @Valid UpdateOrigin updateOrigin) throws NotFoundException
    {
        originsService.updateOrigin(id, updateOrigin);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/places/{id}")
    public ResponseEntity<Void> updatePlace(@PathVariable int id, @RequestBody @Valid UpdatePlace updatePlace) throws NotFoundException
    {
        originsService.updatePlace(id, updatePlace);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrigin(@PathVariable int id) throws NotFoundException
    {
        originsService.deleteOrigin(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/places/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable int id) throws NotFoundException
    {
        originsService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}