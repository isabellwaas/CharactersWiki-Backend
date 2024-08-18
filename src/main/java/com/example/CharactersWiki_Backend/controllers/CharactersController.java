package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;
import com.example.CharactersWiki_Backend.services.ICharactersService;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/characters")
@Validated
public class CharactersController
{
    private final ICharactersService charactersService;

    @Autowired
    public CharactersController(ICharactersService charactersService)
    {
        this.charactersService = charactersService;
    }
    @GetMapping("")
    public ResponseEntity<CharactersResponse> getCharacters(@RequestParam Optional<String> query, @RequestParam int pageNumber, @RequestParam int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getCharacters(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/allegiances")
    public ResponseEntity<AllegiancesResponse> getAllegiances(@RequestParam Optional<String> query, @RequestParam int pageNumber, @RequestParam int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getAllegiances(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/weapons")
    public ResponseEntity<WeaponsResponse> getWeapons(@RequestParam Optional<String> query, @RequestParam int pageNumber, @RequestParam int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getWeapons(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/quotes")
    public ResponseEntity<QuotesResponse> getQuotes(@RequestParam Optional<String> query, @RequestParam int pageNumber, @RequestParam int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getQuotes(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacter(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(charactersService.getCharacterById(id));
    }

    @GetMapping("/allegiances/{id}")
    public ResponseEntity<AllegianceResponse> getAllegiance(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(charactersService.getAllegianceById(id));
    }

    @GetMapping("/weapons/{id}")
    public ResponseEntity<WeaponResponse> getWeapon(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(charactersService.getWeaponById(id));
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<QuoteResponse> getQuote(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(charactersService.getQuoteById(id));
    }

    @PostMapping("")
    public ResponseEntity<IdResponse> createCharacter(@RequestBody @Valid CreateCharacter createCharacter) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createCharacter(createCharacter));
    }

    @PostMapping("/allegiances")
    public ResponseEntity<IdResponse> createAllegiance(@RequestBody @Valid CreateAllegiance createAllegiance) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createAllegiance(createAllegiance));
    }

    @PostMapping("/weapons")
    public ResponseEntity<IdResponse> createWeapon(@RequestBody @Valid CreateWeapon createWeapon) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createWeapon(createWeapon));
    }

    @PostMapping("/quotes")
    public ResponseEntity<IdResponse> createQuote(@RequestBody @Valid CreateQuote createQuote) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createQuote(createQuote));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCharacter(@PathVariable int id, @RequestBody @Valid UpdateCharacter updateCharacter) throws NotFoundException
    {
        charactersService.updateCharacter(id, updateCharacter);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/allegiances/{id}")
    public ResponseEntity<Void> deleteAllegiance(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteAllegiance(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/weapons/{id}")
    public ResponseEntity<Void> deleteWeapon(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteWeapon(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }
}
