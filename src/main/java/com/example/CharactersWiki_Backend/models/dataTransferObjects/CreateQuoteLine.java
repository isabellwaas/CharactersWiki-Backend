package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

public record CreateQuoteLine(
        @NonNull @NotBlank(message="characterName must not be empty.") @Size(min=1, max=300, message="characterName must have between {min} and {max} characters.") String characterName,
        @NonNull @NotBlank(message="text must not be empty.") @Size(min=1, max=300, message="text must have between {min} and {max} characters.") String text
) {}
