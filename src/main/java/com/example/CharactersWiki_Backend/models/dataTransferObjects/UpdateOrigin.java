package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateOrigin(
        @Nullable @Size(max=100, message="name must have between {min} and {max} characters.") String name,
        @Nullable List<Integer> importantPlacesIds,
        @Nullable @Size(max=500, message="description must not be longer than {max} characters.") String description,
        @Nullable List<Integer> charactersIds
) {}