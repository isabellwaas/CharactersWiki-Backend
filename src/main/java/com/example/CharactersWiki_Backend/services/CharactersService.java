package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.*;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;
import com.example.CharactersWiki_Backend.repositories.*;
import com.example.CharactersWiki_Backend.utilities.IControllerHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharactersService implements ICharactersService
{
    private final CharactersRepository charactersRepository;
    private final AllegiancesRepository allegiancesRepository;
    private final WeaponsRepository weaponsRepository;

    private final QuotesRepository quotesRepository;

    private final OriginsRepository originsRepository;
    private final IControllerHelper controllerHelper;


    public CharactersService(CharactersRepository charactersRepository, AllegiancesRepository allegiancesRepository, WeaponsRepository weaponsRepository, QuotesRepository quotesRepository, OriginsRepository originsRepository, IControllerHelper controllerHelper)
    {
        this.charactersRepository = charactersRepository;
        this.allegiancesRepository = allegiancesRepository;
        this.weaponsRepository = weaponsRepository;
        this.quotesRepository = quotesRepository;
        this.originsRepository = originsRepository;
        this.controllerHelper = controllerHelper;
    }

    public CharacterResponse getCharacterById(int id) throws NotFoundException
    {
        return charactersRepository.getCharacterById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));
    }

    public AllegianceResponse getAllegianceById(int id) throws NotFoundException
    {
        return allegiancesRepository.getAllegianceById(id).orElseThrow(() -> new NotFoundException("Allegiance with id " + id + " not found."));
    }

    public WeaponResponse getWeaponById(int id) throws NotFoundException
    {
        return weaponsRepository.getWeaponById(id).orElseThrow(() -> new NotFoundException("Weapon with id " + id + " not found."));
    }

    public QuoteResponse getQuoteById(int id) throws NotFoundException
    {
        return quotesRepository.getQuoteById(id).orElseThrow(() -> new NotFoundException("Quote with id " + id + " not found."));
    }

    public IdResponse createCharacter(CreateCharacter createCharacter) throws NotFoundException
    {
        Character character = new Character(createCharacter.firstname(), createCharacter.age(), createCharacter.species(), createCharacter.status(), createCharacter.hair(), createCharacter.eyes(), createCharacter.skin(), createCharacter.gender());

        if (createCharacter.title() != null) character.setTitle(createCharacter.title());
        if (createCharacter.lastname() != null) character.setLastname(createCharacter.lastname());
        if (createCharacter.nickname() != null) character.setNickname(createCharacter.nickname());
        if (createCharacter.biography() != null) character.setBiography(createCharacter.biography());

        if (createCharacter.originId().isPresent())
            character.setOrigin(originsRepository.findById(createCharacter.originId().get()).orElseThrow(() -> new NotFoundException("Origin with id " + createCharacter.originId() + " not found.")));

        if (createCharacter.allegiancesIds().isPresent())
            character.setAllegiances(allegiancesRepository.findAllegiancesByIdIn(createCharacter.allegiancesIds().get()));
        if (createCharacter.familyIds().isPresent())
            character.setFamily(charactersRepository.findCharactersByIdIn(createCharacter.familyIds().get()));
        if (createCharacter.childrenIds().isPresent())
            character.setChildren(charactersRepository.findCharactersByIdIn(createCharacter.childrenIds().get()));
        if (createCharacter.loveInterestsIds().isPresent())
            character.setLoveInterests(charactersRepository.findCharactersByIdIn(createCharacter.loveInterestsIds().get()));
        if (createCharacter.alliesIds().isPresent())
            character.setAllies(charactersRepository.findCharactersByIdIn(createCharacter.alliesIds().get()));
        if (createCharacter.enemiesIds().isPresent())
            character.setEnemies(charactersRepository.findCharactersByIdIn(createCharacter.enemiesIds().get()));
        if (createCharacter.weaponsIds().isPresent())
            character.setWeapons(weaponsRepository.findWeaponsByIdIn(createCharacter.weaponsIds().get()));
        if (createCharacter.quotesIds().isPresent())
            character.setQuotes(quotesRepository.findQuotesByIdIn(createCharacter.quotesIds().get()));

        charactersRepository.save(character);
        charactersRepository.flush();

        return new IdResponse(character.getId());
    }

    public IdResponse createAllegiance(CreateAllegiance createAllegiance) throws NotFoundException
    {
        Allegiance allegiance = new Allegiance(createAllegiance.name());
        if (createAllegiance.note() != null) allegiance.setNote(createAllegiance.note());

        allegiancesRepository.save(allegiance);
        allegiancesRepository.flush();

        return new IdResponse(allegiance.getId());
    }

    public IdResponse createWeapon(CreateWeapon createWeapon) throws NotFoundException
    {
        Weapon weapon = new Weapon(createWeapon.name());

        weaponsRepository.save(weapon);
        weaponsRepository.flush();

        return new IdResponse(weapon.getId());
    }

    public IdResponse createQuote(CreateQuote createQuote) throws NotFoundException
    {
        Quote quote = new Quote();

        List<QuoteLine> quoteLines = new ArrayList<>();
        for (CreateQuoteLine createQuoteLine : createQuote.quoteLines())
        {
            QuoteLine quoteLine = new QuoteLine(createQuoteLine.text());
            quoteLine.setCharacter(charactersRepository.findById(createQuoteLine.characterId()).orElseThrow(() -> new NotFoundException("Character with id " + createQuoteLine.characterId() + " not found.")));
            quoteLines.add(quoteLine);
        }
        quote.setQuoteLines(quoteLines);

        if (createQuote.charactersIds().isPresent()) quote.setCharacters(charactersRepository.findCharactersByIdIn(createQuote.charactersIds().get()));

        quotesRepository.save(quote);
        quotesRepository.flush();
        // TODO Save quotelines and characters

        return new IdResponse(quote.getId());
    }
}
