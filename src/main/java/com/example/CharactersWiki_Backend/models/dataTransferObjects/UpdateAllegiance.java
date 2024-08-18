package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

public record UpdateAllegiance(
        @Nullable @Size(max=300, message="name must have between {min} and {max} characters.") String name,
        @Nullable @Size(max=300, message="note must not be longer than {max} characters.") String note
) {}