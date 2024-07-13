package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.*;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.responses.*;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateCharacter;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.repositories.CharactersRepository;
import com.example.CharactersWiki_Backend.repositories.OriginsRepository;
import com.example.CharactersWiki_Backend.services.ICharactersService;
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
    private final ICharactersService charactersService;

    @Autowired
    public CharactersController(ICharactersService charactersService, IControllerHelper controllerHelper)
    {
        this.charactersService = charactersService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponse> getCharacter(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(charactersService.getCharacterById(id));
    }

    @PostMapping("/")
    public ResponseEntity<IdResponse> createCharacter(@RequestBody @Valid CreateCharacter createCharacter) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createCharacter(createCharacter));
    }
}
