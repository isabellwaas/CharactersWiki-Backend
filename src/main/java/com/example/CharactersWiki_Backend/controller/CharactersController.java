package com.example.CharactersWiki_Backend.controller;

import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.repositories.CharactersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/characters")
@Validated
public class CharactersController
{
    private final CharactersRepository charactersRepository;

    @Autowired
    public CharactersController(CharactersRepository charactersRepository)
    {
        this.charactersRepository = charactersRepository;
    }

    @GetMapping("/id")
    public ResponseEntity<Character> getCharacter(@PathVariable int id) throws NotFoundException
    {
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id "+id+" not found."));
        return ResponseEntity.ok(character);
    }
}
