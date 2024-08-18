package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;


@Data
public class CreateCharacter extends CharacterRelationships {
        @Nullable @Size(max=100, message="title must not be longer than {max} characters.") String title;
        @NonNull @NotBlank(message="firstname must not be empty.") @Size(min=1, max=100, message="firstname must have between {min} and {max} characters.") String firstname;
        @Nullable @Size(max=100, message="lastname must not be longer than {max} characters.") String lastname;
        @Nullable @Size(max=100, message="nickname must not be longer than {max} characters.") String nickname;
        @NonNull int age;
        @NonNull @NotBlank(message="species must not be empty.") @Size(min=1, max=50, message="species must have between {min} and {max} characters.") String species;
        @NonNull @NotBlank(message="status must not be empty.") @Size(min=1, max=30, message="status must have between {min} and {max} characters.") String status;
        @NonNull @NotBlank(message="hair must not be empty.") @Size(min=1, max=30, message="hair must have between {min} and {max} characters.") String hair;
        @NonNull @NotBlank(message="eyes must not be empty.") @Size(min=1, max=30, message="eyes must have between {min} and {max} characters.") String eyes;
        @NonNull @NotBlank(message="skin must not be empty.") @Size(min=1, max=30, message="skin must have between {min} and {max} characters.") String skin;
        @NonNull @NotBlank(message="gender must not be empty.") @Size(min=1, max=50, message="gender must have between {min} and {max} characters.") String gender;
        @Nullable @Size(max=500, message="biography must not be longer than {max} characters.") String biography;
}
