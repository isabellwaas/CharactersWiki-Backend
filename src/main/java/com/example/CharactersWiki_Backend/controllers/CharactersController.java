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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    //TODO: required false statt Optional
    public ResponseEntity<CharactersResponse> getCharacters(@RequestParam Optional<String> query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getCharacters(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/allegiances")
    public ResponseEntity<AllegiancesResponse> getAllegiances(@RequestParam Optional<String> query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getAllegiances(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/weapons")
    public ResponseEntity<WeaponsResponse> getWeapons(@RequestParam Optional<String> query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getWeapons(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/quotes")
    public ResponseEntity<QuotesResponse> getQuotes(@RequestParam Optional<String> query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam Optional<SortDirection> sortDirection)
    {
        return ResponseEntity.ok(charactersService.getQuotes(query, pageNumber, perPage, sortDirection));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CharacterResponse>> getCharacter(@PathVariable int id) throws NotFoundException
    {
        EntityModel<CharacterResponse> entityModel=EntityModel.of(charactersService.getCharacterById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacter(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateCharacter(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteCharacter(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(Optional.empty(), 1, 10, Optional.empty())).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/allegiances/{id}")
    public ResponseEntity<EntityModel<AllegianceResponse>> getAllegiance(@PathVariable int id) throws NotFoundException
    {
        EntityModel<AllegianceResponse> entityModel=EntityModel.of(charactersService.getAllegianceById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiance(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateAllegiance(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteAllegiance(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(Optional.empty(), 1, 10, Optional.empty())).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/weapons/{id}")
    public ResponseEntity<EntityModel<WeaponResponse>> getWeapon(@PathVariable int id) throws NotFoundException
    {
        EntityModel<WeaponResponse> entityModel=EntityModel.of(charactersService.getWeaponById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapon(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateWeapon(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteWeapon(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(Optional.empty(), 1, 10, Optional.empty())).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<EntityModel<QuoteResponse>> getQuote(@PathVariable int id) throws NotFoundException
    {
        EntityModel<QuoteResponse> entityModel=EntityModel.of(charactersService.getQuoteById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuote(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateQuote(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteQuote(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(Optional.empty(), 1, 10, Optional.empty())).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
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

    @PatchMapping("/allegiances/{id}")
    public ResponseEntity<Void> updateAllegiance(@PathVariable int id, @RequestBody @Valid UpdateAllegiance updateAllegiance) throws NotFoundException
    {
        charactersService.updateAllegiance(id, updateAllegiance);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/weapons/{id}")
    public ResponseEntity<Void> updateWeapon(@PathVariable int id, @RequestBody @Valid UpdateWeapon updateWeapon) throws NotFoundException
    {
        charactersService.updateWeapon(id, updateWeapon);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/quotes/{id}")
    public ResponseEntity<IdResponse> updateQuote(@PathVariable int id, @RequestBody @Valid UpdateQuote updateQuote) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.updateQuote(id, updateQuote));
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
