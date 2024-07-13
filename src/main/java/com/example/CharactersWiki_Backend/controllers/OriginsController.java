package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.responses.IdResponse;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateOrigin;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.services.IOriginsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/origins")
@Validated
public class OriginsController
{
    private final IOriginsService originsService;

    @Autowired
    public OriginsController(IOriginsService originsService)
    {
        this.originsService = originsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OriginResponse> getOrigin(@PathVariable int id) throws NotFoundException
    {
        return ResponseEntity.ok(originsService.getOriginById(id));
    }

    @PostMapping("/")
    public ResponseEntity<IdResponse> createOrigin(@RequestBody @Valid CreateOrigin createOrigin) throws NotFoundException
    {
        return ResponseEntity.status(201).body(originsService.createOrigin(createOrigin));
    }
}