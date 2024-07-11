package com.example.CharactersWiki_Backend.controller;

import com.example.CharactersWiki_Backend.models.*;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.ProjectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.ProjectionInterfaces.CharacterSummary;
import com.example.CharactersWiki_Backend.models.Responses.*;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateCharacter;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.repositories.CharactersRepository;
import com.example.CharactersWiki_Backend.repositories.OriginsRepository;
import com.example.CharactersWiki_Backend.utilities.IControllerHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/characters")
@Validated
public class CharactersController
{
    private final CharactersRepository charactersRepository;
    private final OriginsRepository originsRepository;
    private final IControllerHelper controllerHelper;

    @Autowired
    public CharactersController(CharactersRepository charactersRepository, OriginsRepository originsRepository, IControllerHelper controllerHelper)
    {
        this.charactersRepository = charactersRepository;
        this.originsRepository=originsRepository;
        this.controllerHelper = controllerHelper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacter(@PathVariable int id) throws NotFoundException
    {
        CharacterResponse character = charactersRepository.getCharacterById(id).orElseThrow(() -> new NotFoundException("Character with id "+id+" not found."));
        return ResponseEntity.ok(character);
    }

    @PostMapping("/")
    public ResponseEntity<IdResponse> createCharacter(@RequestBody @Valid CreateCharacter createCharacter) throws NotFoundException
    {
        Character character = new Character(createCharacter.firstname(), createCharacter.age(), createCharacter.species(), createCharacter.status(), createCharacter.hair(), createCharacter.eyes(), createCharacter.skin(), createCharacter.gender());

        if(createCharacter.originId().isPresent())
        {
            Origin origin=originsRepository.findById(createCharacter.originId().get()).orElseThrow(() -> new NotFoundException("Origin with id "+createCharacter.originId()+" not found."));
            character.setOrigin(origin);
        }

        //Allegiances, Family, children, loveInterests, allies, enemies, weapons, quotes

        charactersRepository.save(character);
        charactersRepository.flush();

        return ResponseEntity.status(201).body(new IdResponse(character.getId()));
    }
}
