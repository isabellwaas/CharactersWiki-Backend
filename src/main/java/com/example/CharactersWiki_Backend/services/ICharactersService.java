package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateCharacter;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.responses.IdResponse;

import java.util.Optional;

public interface ICharactersService
{
    public CharacterResponse getCharacterById(int id) throws NotFoundException;
    public IdResponse createCharacter(CreateCharacter createCharacter) throws NotFoundException;
}
