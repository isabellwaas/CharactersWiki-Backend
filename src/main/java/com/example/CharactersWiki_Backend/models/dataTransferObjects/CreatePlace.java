package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.util.Optional;

public record CreatePlace(
        @NonNull @NotBlank(message="name must not be empty.") @Size(min=1, max=100, message="name must have between {min} and {max} characters.") String name,
        @NonNull @NotBlank(message="type must not be empty.") @Size(min=1, max=100, message="type must have between {min} and {max} characters.") String type,
        @Nullable Integer originId
){}
