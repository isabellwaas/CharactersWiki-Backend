package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateOrigin;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.IdResponse;

public interface IOriginsService
{
    public OriginResponse getOriginById(int id) throws NotFoundException;
    public IdResponse createOrigin(CreateOrigin createOrigin) throws NotFoundException;
}
