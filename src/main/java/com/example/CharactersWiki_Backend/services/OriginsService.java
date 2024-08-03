package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.Origin;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateOrigin;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.IdResponse;
import com.example.CharactersWiki_Backend.repositories.OriginsRepository;
import org.springframework.stereotype.Service;

@Service
public class OriginsService implements IOriginsService
{
    private final OriginsRepository originsRepository;

    public OriginsService(OriginsRepository originsRepository)
    {
        this.originsRepository = originsRepository;
    }

    public OriginResponse getOriginById(int id) throws NotFoundException
    {
        return originsRepository.findOriginById(id).orElseThrow(() -> new NotFoundException("Origin with id "+id+" not found."));
    }

    public IdResponse createOrigin(CreateOrigin createOrigin) throws NotFoundException
    {
        Origin origin = new Origin(createOrigin.name());
        //importantPlaces, description, Characters

        originsRepository.save(origin);
        originsRepository.flush();

        return new IdResponse(origin.getId());
    }
}
