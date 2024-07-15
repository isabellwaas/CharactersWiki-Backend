package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;

public interface ICharactersService
{
    public CharacterResponse getCharacterById(int id) throws NotFoundException;
    public AllegianceResponse getAllegianceById(int id) throws NotFoundException;
    public WeaponResponse getWeaponById(int id) throws NotFoundException;
    public QuoteResponse getQuoteById(int id) throws NotFoundException;
    public IdResponse createCharacter(CreateCharacter createCharacter) throws NotFoundException;
    public IdResponse createAllegiance(CreateAllegiance createAllegiance) throws NotFoundException;
    public IdResponse createWeapon(CreateWeapon createWeapon) throws NotFoundException;
    public IdResponse createQuote(CreateQuote createQuote) throws NotFoundException;
}
