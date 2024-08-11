package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public record CreateOrigin(
        @NonNull @NotBlank(message="name must not be empty.") @Size(min=1, max=100, message="name must have between {min} and {max} characters.") String name,
        Optional<List<Integer>> importantPlacesIds,
        @Nullable @Size(max=500, message="description must not be longer than {max} characters.") String description,
        Optional<List<Integer>> charactersIds
) {}
