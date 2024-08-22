package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

public record UpdatePlace(
        @Nullable @Size(max=100, message="name must have between {min} and {max} characters.") String name,
        @Nullable @Size(max=100, message="type must have between {min} and {max} characters.") String type,
        @Nullable Integer originId
){}
