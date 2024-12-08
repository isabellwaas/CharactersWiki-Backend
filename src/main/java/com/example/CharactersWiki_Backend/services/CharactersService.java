package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.*;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.*;
import com.example.CharactersWiki_Backend.repositories.*;
import com.example.CharactersWiki_Backend.utilities.Quality;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final IImagesService imagesService;

    public CharactersService(CharactersRepository charactersRepository, AllegiancesRepository allegiancesRepository, WeaponsRepository weaponsRepository, QuotesRepository quotesRepository, OriginsRepository originsRepository, IImagesService imagesService)
    {
        this.charactersRepository = charactersRepository;
        this.allegiancesRepository = allegiancesRepository;
        this.weaponsRepository = weaponsRepository;
        this.quotesRepository = quotesRepository;
        this.originsRepository = originsRepository;
        this.imagesService = imagesService;
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

    public byte[] getImageOfCharacter(int id, Quality quality) throws Exception
    {
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));
        if(character.getImage() == null) throw new NotFoundException("Character with id " + id + " has no image.");
        return imagesService.get(character.getImage().getId(), quality).orElseThrow(() -> new NotFoundException("Image in quality "+ quality +" for character with id " + id + " not found."));
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
        Character character = new Character(createCharacter.getFirstname(), createCharacter.getAge(), createCharacter.getSpecies(), createCharacter.getStatus(), createCharacter.getHair(), createCharacter.getEyes(), createCharacter.getSkin(), createCharacter.getGender());

        if (createCharacter.getTitle() != null) character.setTitle(createCharacter.getTitle());
        if (createCharacter.getLastname() != null) character.setLastname(createCharacter.getLastname());
        if (createCharacter.getNickname() != null) character.setNickname(createCharacter.getNickname());
        if (createCharacter.getBiography() != null) character.setBiography(createCharacter.getBiography());

        //TODO: Fehler wenn id nicht vorhanden, aber falsche fehlermeldung angezeigt (Timestamp...?)
        updateCharacterRelationships(character, createCharacter);

        charactersRepository.save(character);
        charactersRepository.flush();

        return new IdResponse(character.getId());
    }

    public IdResponse createImageForCharacter(int id, MultipartFile file) throws Exception
    {
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));

        Image image = imagesService.save(file);
        character.setImage(image);
        charactersRepository.save(character);
        charactersRepository.flush();

        return new IdResponse(image.getId());
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
        setQuoteLinesForQuote(createQuote.quoteLines(), quote);

        if (createQuote.charactersIds() != null)
        {
            List<Character> characters=charactersRepository.findCharactersByIdIn(createQuote.charactersIds());
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

    public void updateCharacter(int id, UpdateCharacter updateCharacter) throws NotFoundException
    {
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));

        if (updateCharacter.getTitle() != null) character.setTitle(updateCharacter.getTitle());
        if (updateCharacter.getFirstname() != null) character.setFirstname(updateCharacter.getFirstname());
        if (updateCharacter.getLastname() != null) character.setLastname(updateCharacter.getLastname());
        if (updateCharacter.getNickname() != null) character.setNickname(updateCharacter.getNickname());
        if (updateCharacter.getAge() != null) character.setAge(updateCharacter.getAge());
        if (updateCharacter.getSpecies() != null) character.setSpecies(updateCharacter.getSpecies());
        if (updateCharacter.getStatus() != null) character.setStatus(updateCharacter.getStatus());
        if (updateCharacter.getHair() != null) character.setHair(updateCharacter.getHair());
        if (updateCharacter.getEyes() != null) character.setEyes(updateCharacter.getEyes());
        if (updateCharacter.getSkin() != null) character.setSkin(updateCharacter.getSkin());
        if (updateCharacter.getGender() != null) character.setGender(updateCharacter.getGender());
        if (updateCharacter.getBiography() != null) character.setBiography(updateCharacter.getBiography());

        updateCharacterRelationships(character, updateCharacter);

        charactersRepository.save(character);
        charactersRepository.flush();
    }

    private void updateCharacterRelationships(Character character, CharacterRelationships characterRelationships) throws NotFoundException
    {
        if (characterRelationships.originId != null)
            character.setOrigin(originsRepository.findById(characterRelationships.originId).orElseThrow(() -> new NotFoundException("Origin with id " + characterRelationships.originId + " not found.")));

        if(characterRelationships.allegiancesIds != null) character.setAllegiances(allegiancesRepository.findAllegiancesByIdIn(characterRelationships.allegiancesIds));
        if(characterRelationships.familyIds != null) character.setFamily(charactersRepository.findCharactersByIdIn(characterRelationships.familyIds));
        if(characterRelationships.childrenIds != null) character.setChildren(charactersRepository.findCharactersByIdIn(characterRelationships.childrenIds));
        if(characterRelationships.loveInterestsIds != null) character.setLoveInterests(charactersRepository.findCharactersByIdIn(characterRelationships.loveInterestsIds));
        if(characterRelationships.alliesIds != null) character.setAllies(charactersRepository.findCharactersByIdIn(characterRelationships.alliesIds));
        if(characterRelationships.enemiesIds != null) character.setEnemies(charactersRepository.findCharactersByIdIn(characterRelationships.enemiesIds));
        if(characterRelationships.weaponsIds != null) character.setWeapons(weaponsRepository.findWeaponsByIdIn(characterRelationships.weaponsIds));
        if(characterRelationships.quotesIds != null) character.setQuotes(quotesRepository.findQuotesByIdIn(characterRelationships.quotesIds));
    }

    public void updateAllegiance(int id, UpdateAllegiance updateAllegiance) throws NotFoundException
    {
        Allegiance allegiance = allegiancesRepository.findById(id).orElseThrow(() -> new NotFoundException("Allegiance with id " + id + " not found."));

        if (updateAllegiance.name() != null) allegiance.setName(updateAllegiance.name());
        if (updateAllegiance.note() != null) allegiance.setNote(updateAllegiance.note());

        allegiancesRepository.save(allegiance);
        allegiancesRepository.flush();
    }
    public void updateWeapon(int id, UpdateWeapon updateWeapon) throws NotFoundException
    {
        Weapon weapon = weaponsRepository.findById(id).orElseThrow(() -> new NotFoundException("Weapon with id " + id + " not found."));

        if (updateWeapon.name() != null) weapon.setName(updateWeapon.name());

        weaponsRepository.save(weapon);
        weaponsRepository.flush();
    }

    public IdResponse updateQuote(int id, UpdateQuote updateQuote) throws NotFoundException
    {
        Quote currentQuote = quotesRepository.findById(id).orElseThrow(() -> new NotFoundException("Quote with id " + id + " not found."));
        Quote newQuote = new Quote();

        if(updateQuote.quoteLines() != null)
        {
            setQuoteLinesForQuote(updateQuote.quoteLines(), newQuote);
        }
        else
        {
            List<QuoteLine> quoteLines = currentQuote.getQuoteLines();
            quoteLines.forEach(quoteLine -> quoteLine.setQuote(newQuote));
            newQuote.setQuoteLines(quoteLines);
        }

        List<Character> characters=new ArrayList<>();
        if (updateQuote.charactersIds() != null) characters=charactersRepository.findCharactersByIdIn(updateQuote.charactersIds());
        else characters=currentQuote.getCharacters();

        deleteQuoteFromCharacters(currentQuote, currentQuote.getCharacters());
        newQuote.setCharacters(characters);
        characters.forEach(character ->
        {
            List<Quote> quotes=character.getQuotes();
            quotes.add(newQuote);
            character.setQuotes(quotes);
        });

        quotesRepository.delete(currentQuote);
        quotesRepository.save(newQuote);
        quotesRepository.flush();

        return new IdResponse(newQuote.getId());
    }

    private void setQuoteLinesForQuote(List<CreateQuoteLine> createQuoteLines, Quote quote)
    {
        List<QuoteLine> quoteLines = new ArrayList<>();
        QuoteLine currentQuoteLine;
        for (CreateQuoteLine createQuoteLine : createQuoteLines)
        {
            currentQuoteLine=new QuoteLine(createQuoteLine.characterName(), createQuoteLine.text());
            currentQuoteLine.setQuote(quote);
            quoteLines.add(currentQuoteLine);
        }
        quote.setQuoteLines(quoteLines);
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

    public void deleteImageOfCharacter(int id) throws Exception
    {
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id " + id + " not found."));
        if(character.getImage() == null) throw new NotFoundException("Character with id " + id + " has no image.");

        int imageID = character.getImage().getId();
        character.setImage(null);
        charactersRepository.save(character);

        imagesService.delete(imageID);
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
        deleteQuoteFromCharacters(quote, quote.getCharacters());
        quotesRepository.delete(quote);
    }

    private void deleteQuoteFromCharacters(Quote quote, List<Character> characters)
    {
        characters.forEach(character ->
        {
            List<Quote> quotes=character.getQuotes();
            quotes.remove(quote);
            character.setQuotes(quotes);
        });
    }
}
