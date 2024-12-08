package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;
import com.example.CharactersWiki_Backend.utilities.Quality;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ICharactersService
{
    public CharactersResponse getCharacters(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection);
    public AllegiancesResponse getAllegiances(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection);
    public WeaponsResponse getWeapons(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection);
    public QuotesResponse getQuotes(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection);

    public CharacterResponse getCharacterById(int id) throws NotFoundException;
    public byte[] getImageOfCharacter(int id, Quality quality) throws Exception;
    public AllegianceResponse getAllegianceById(int id) throws NotFoundException;
    public WeaponResponse getWeaponById(int id) throws NotFoundException;
    public QuoteResponse getQuoteById(int id) throws NotFoundException;
    public IdResponse createCharacter(CreateCharacter createCharacter) throws NotFoundException;
    public IdResponse createAllegiance(CreateAllegiance createAllegiance) throws NotFoundException;
    public IdResponse createWeapon(CreateWeapon createWeapon) throws NotFoundException;
    public IdResponse createQuote(CreateQuote createQuote) throws NotFoundException;
    public IdResponse createImageForCharacter(int id, MultipartFile file) throws Exception;
    public void updateCharacter(int id, UpdateCharacter updateCharacter) throws NotFoundException;
    public void updateAllegiance(int id, UpdateAllegiance updateAllegiance) throws NotFoundException;
    public void updateWeapon(int id, UpdateWeapon updateWeapon) throws NotFoundException;
    public IdResponse updateQuote(int id, UpdateQuote updateQuote) throws NotFoundException;
    public void deleteCharacter(int id) throws NotFoundException;
    public void deleteImageOfCharacter(int id) throws Exception;
    public void deleteAllegiance(int id) throws NotFoundException;
    public void deleteWeapon(int id) throws NotFoundException;
    public void deleteQuote(int id) throws NotFoundException;
}
