package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.Allegiance;
import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.Origin;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateCharacter;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.responses.IdResponse;
import com.example.CharactersWiki_Backend.repositories.CharactersRepository;
import com.example.CharactersWiki_Backend.repositories.OriginsRepository;
import com.example.CharactersWiki_Backend.utilities.IControllerHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharactersService implements ICharactersService
{
    private final CharactersRepository charactersRepository;
    private final OriginsRepository originsRepository;
    private final IControllerHelper controllerHelper;


    public CharactersService(CharactersRepository charactersRepository, OriginsRepository originsRepository, IControllerHelper controllerHelper)
    {
        this.charactersRepository = charactersRepository;
        this.originsRepository = originsRepository;
        this.controllerHelper = controllerHelper;
    }

    public CharacterResponse getCharacterById(int id) throws NotFoundException
    {
        return charactersRepository.getCharacterById(id).orElseThrow(() -> new NotFoundException("Character with id "+id+" not found."));
    }

    public IdResponse createCharacter(CreateCharacter createCharacter) throws NotFoundException
    {
        Character character = new Character(createCharacter.firstname(), createCharacter.age(), createCharacter.species(), createCharacter.status(), createCharacter.hair(), createCharacter.eyes(), createCharacter.skin(), createCharacter.gender());

        if(createCharacter.originId().isPresent())
        {
            Origin origin=originsRepository.findById(createCharacter.originId().get()).orElseThrow(() -> new NotFoundException("Origin with id "+createCharacter.originId()+" not found."));
            character.setOrigin(origin);
        }

        // TODO: Bisher ist nur Allegiance Optional Type
        List<Allegiance> allegiances = new ArrayList<>();
        /*if(createCharacter.allegianceIds().isPresent())
        {
            for(int allegianceId : createCharacter.allegianceIds().get())
            {
                Allegiance allegiance = controllerHelper.getAllegiance(allegianceId);
                allegiances.add(allegiance);
            }
        }
        character.setAllegiances(allegiances);*/

        //Allegiances, Family, children, loveInterests, allies, enemies, weapons, quotes

        charactersRepository.save(character);
        charactersRepository.flush();

        return new IdResponse(character.getId());
    }
}
