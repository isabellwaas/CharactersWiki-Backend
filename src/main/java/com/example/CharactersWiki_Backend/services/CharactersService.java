package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.*;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.*;
import com.example.CharactersWiki_Backend.repositories.*;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharactersService implements ICharactersService
{
    private final CharactersRepository charactersRepository;
    private final AllegiancesRepository allegiancesRepository;
    private final WeaponsRepository weaponsRepository;

    private final QuotesRepository quotesRepository;

    private final OriginsRepository originsRepository;

    public CharactersService(CharactersRepository charactersRepository, AllegiancesRepository allegiancesRepository, WeaponsRepository weaponsRepository, QuotesRepository quotesRepository, OriginsRepository originsRepository)
    {
        this.charactersRepository = charactersRepository;
        this.allegiancesRepository = allegiancesRepository;
        this.weaponsRepository = weaponsRepository;
        this.quotesRepository = quotesRepository;
        this.originsRepository = originsRepository;
    }

    public CharactersResponse getCharacters(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection)
    {
        Sort sort = (sortDirection.isEmpty() || sortDirection.get() == SortDirection.ASC) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Page<CharacterSummary> page = charactersRepository.findCharactersByFirstnameContainingOrLastnameContainingOrNicknameContainingOrTitleContaining(query.orElse(""), query.orElse(""), query.orElse(""), query.orElse(""), PageRequest.of(pageNumber-1, perPage, sort));
        return new CharactersResponse(page.getTotalPages(), page.getContent());
    }

    public AllegiancesResponse getAllegiances(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection)
    {
        Sort sort = (sortDirection.isEmpty() || sortDirection.get() == SortDirection.ASC) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Page<AllegianceSummary> page = allegiancesRepository.findAllegiancesByNameContainingOrNoteContaining(query.orElse(""), query.orElse(""), PageRequest.of(pageNumber-1, perPage, sort));
        return new AllegiancesResponse(page.getTotalPages(), page.getContent());
    }

    public WeaponsResponse getWeapons(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection)
    {
        Sort sort = (sortDirection.isEmpty() || sortDirection.get() == SortDirection.ASC) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Page<WeaponSummary> page = weaponsRepository.findWeaponsByNameContaining(query.orElse(""), PageRequest.of(pageNumber-1, perPage, sort));
        return new WeaponsResponse(page.getTotalPages(), page.getContent());
    }

    public QuotesResponse getQuotes(Optional<String> query, int pageNumber, int perPage, Optional<SortDirection> sortDirection)
    {
        Sort sort = (sortDirection.isEmpty() || sortDirection.get() == SortDirection.ASC) ? Sort.by("id").ascending() : Sort.by("id").descending();
        Page<QuoteResponse> page = quotesRepository.findQuoteByQuoteLinesTextContaining(query.orElse(""), PageRequest.of(pageNumber-1, perPage, sort));
        return new QuotesResponse(page.getTotalPages(), page.getContent());
    }

    public CharacterResponse getCharacterById(int id) throws NotFoundException
    {
        return charactersRepository.findCharacterById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));
    }

    public AllegianceResponse getAllegianceById(int id) throws NotFoundException
    {
        return allegiancesRepository.findAllegianceById(id).orElseThrow(() -> new NotFoundException("Allegiance with id " + id + " not found."));
    }

    public WeaponResponse getWeaponById(int id) throws NotFoundException
    {
        return weaponsRepository.findWeaponById(id).orElseThrow(() -> new NotFoundException("Weapon with id " + id + " not found."));
    }

    public QuoteResponse getQuoteById(int id) throws NotFoundException
    {
        return quotesRepository.findQuoteById(id).orElseThrow(() -> new NotFoundException("Quote with id " + id + " not found."));
    }

    public IdResponse createCharacter(CreateCharacter createCharacter) throws NotFoundException
    {
        Character character = new Character(createCharacter.firstname(), createCharacter.age(), createCharacter.species(), createCharacter.status(), createCharacter.hair(), createCharacter.eyes(), createCharacter.skin(), createCharacter.gender());

        if (createCharacter.title() != null) character.setTitle(createCharacter.title());
        if (createCharacter.lastname() != null) character.setLastname(createCharacter.lastname());
        if (createCharacter.nickname() != null) character.setNickname(createCharacter.nickname());
        if (createCharacter.biography() != null) character.setBiography(createCharacter.biography());

        //TODO: Fehler wenn id nicht vorhanden, aber falsche fehlermeldung angezeigt (Timestamp...?)
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
        QuoteLine currentQuoteLine;
        for (CreateQuoteLine createQuoteLine : createQuote.quoteLines())
        {
            currentQuoteLine=new QuoteLine(createQuoteLine.characterName(), createQuoteLine.text());
            currentQuoteLine.setQuote(quote);
            quoteLines.add(currentQuoteLine);
        }
        quote.setQuoteLines(quoteLines);

        if (createQuote.charactersIds().isPresent())
        {
            List<Character> characters=charactersRepository.findCharactersByIdIn(createQuote.charactersIds().get());
            quote.setCharacters(characters);
            characters.forEach(character ->
            {
                List<Quote> quotes=character.getQuotes();
                quotes.add(quote);
                character.setQuotes(quotes);
            });
        }

        quotesRepository.save(quote);
        quotesRepository.flush();

        return new IdResponse(quote.getId());
    }

    public void deleteCharacter(int id) throws NotFoundException
    {
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));

        List<Allegiance> allegiances=character.getAllegiances();
        allegiances.forEach(allegiance -> allegiance.getCharacters().remove(character));

        List<Weapon> weapons=character.getWeapons();
        weapons.forEach(weapon -> weapon.getCharacters().remove(character));

        List<Quote> quotes=character.getQuotes();
        quotes.forEach(quote -> quote.getCharacters().remove(character));

        charactersRepository.delete(character);

        quotes.forEach(quote -> {
            if(quote.getCharacters().isEmpty()) quotesRepository.delete(quote);
        });
    }

    public void deleteAllegiance(int id) throws NotFoundException
    {
        Allegiance allegiance = allegiancesRepository.findById(id).orElseThrow(() -> new NotFoundException("Allegiance with id " + id + " not found."));

        List<Character> characters=allegiance.getCharacters();
        characters.forEach(character ->
        {
            List<Allegiance> allegiances=character.getAllegiances();
            allegiances.remove(allegiance);
            character.setAllegiances(allegiances);
        });

        allegiancesRepository.delete(allegiance);
    }
    public void deleteWeapon(int id) throws NotFoundException
    {
        Weapon weapon = weaponsRepository.findById(id).orElseThrow(() -> new NotFoundException("Weapon with id " + id + " not found."));

        List<Character> characters=weapon.getCharacters();
        characters.forEach(character ->
        {
            List<Weapon> weapons=character.getWeapons();
            weapons.remove(weapon);
            character.setWeapons(weapons);
        });

        weaponsRepository.delete(weapon);
    }

    public void deleteQuote(int id) throws NotFoundException
    {
        Quote quote = quotesRepository.findById(id).orElseThrow(() -> new NotFoundException("Quote with id " + id + " not found."));

        List<Character> characters=quote.getCharacters();
        characters.forEach(character ->
        {
            List<Quote> quotes=character.getQuotes();
            quotes.remove(quote);
            character.setQuotes(quotes);
        });

        quotesRepository.delete(quote);
    }
}
