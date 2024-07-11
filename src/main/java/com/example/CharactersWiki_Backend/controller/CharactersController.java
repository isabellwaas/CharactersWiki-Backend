package com.example.CharactersWiki_Backend.controller;

import com.example.CharactersWiki_Backend.models.*;
import com.example.CharactersWiki_Backend.models.Character;
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
        Character character = charactersRepository.findById(id).orElseThrow(() -> new NotFoundException("Character with id "+id+" not found."));
        return ResponseEntity.ok(new CharacterResponse(
                character.getId(),
                character.getTitle(),
                character.getFirstname(),
                character.getLastname(),
                character.getNickname(),
                character.getAge(),
                character.getSpecies(),
                character.getStatus(),
                new ShortOriginResponse(character.getOrigin().getId(),character.getOrigin().getName()),
                character.getHair(),
                character.getEyes(),
                character.getSkin(),
                character.getGender(),
                character.getAllegiances().stream().map((Allegiance allegiance) -> new ShortAllegianceResponse(allegiance.getId(), allegiance.getName(), allegiance.getNote())).toList(),
                character.getFamily().stream().map((Character familyMember) -> new ShortCharacterResponse(familyMember.getId(), familyMember.getTitle(), familyMember.getFirstname(), familyMember.getLastname(), familyMember.getNickname())).toList(),
                character.getChildren().stream().map((Character child) -> new ShortCharacterResponse(child.getId(), child.getTitle(), child.getFirstname(), child.getLastname(), child.getNickname())).toList(),
                character.getLoveInterests().stream().map((Character loveInterest) -> new ShortCharacterResponse(loveInterest.getId(), loveInterest.getTitle(), loveInterest.getFirstname(), loveInterest.getLastname(), loveInterest.getNickname())).toList(),
                character.getAllies().stream().map((Character ally) -> new ShortCharacterResponse(ally.getId(), ally.getTitle(), ally.getFirstname(), ally.getLastname(), ally.getNickname())).toList(),
                character.getEnemies().stream().map((Character enemy) -> new ShortCharacterResponse(enemy.getId(), enemy.getTitle(), enemy.getFirstname(), enemy.getLastname(), enemy.getNickname())).toList(),
                character.getWeapons().stream().map((Weapon weapon) -> new ShortWeaponResponse(weapon.getId(), weapon.getName())).toList(),
                character.getQuotes().stream().map((Quote quote) -> new ShortQuoteResponse(quote.getId())).toList(),
                character.getBiography()
        ));
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
