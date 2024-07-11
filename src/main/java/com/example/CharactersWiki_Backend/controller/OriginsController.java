package com.example.CharactersWiki_Backend.controller;

import com.example.CharactersWiki_Backend.models.Origin;
import com.example.CharactersWiki_Backend.models.Responses.IdResponse;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.CreateOrigin;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.repositories.OriginsRepository;
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
    private final OriginsRepository originsRepository;

    @Autowired
    public OriginsController(OriginsRepository originsRepository)
    {
        this.originsRepository = originsRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Origin> getOrigin(@PathVariable int id) throws NotFoundException
    {
        Origin origin = originsRepository.findById(id).orElseThrow(() -> new NotFoundException("Origin with id "+id+" not found."));
        return ResponseEntity.ok(origin);
    }

    @PostMapping("/")
    public ResponseEntity<IdResponse> createOrigin(@RequestBody @Valid CreateOrigin createOrigin) throws NotFoundException
    {
        System.out.println("hello origin");
        Origin origin = new Origin(createOrigin.name());
        //importantPlaces, description, Characters

        originsRepository.save(origin);
        originsRepository.flush();

        return ResponseEntity.status(201).body(new IdResponse(origin.getId()));
    }
}