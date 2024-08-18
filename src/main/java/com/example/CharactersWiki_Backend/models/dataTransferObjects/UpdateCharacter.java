package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.annotation.Nullable;

@Data
public class UpdateCharacter extends CharacterRelationships
{
        @Nullable @Size(max=100, message="title must not be longer than {max} characters.") String title;
        @Nullable @Size(max=100, message="firstname must have between {min} and {max} characters.") String firstname;
        @Nullable @Size(max=100, message="lastname must not be longer than {max} characters.") String lastname;
        @Nullable @Size(max=100, message="nickname must not be longer than {max} characters.") String nickname;
        @Nullable Integer age;
        @Nullable @Size(max=50, message="species must have between {min} and {max} characters.") String species;
        @Nullable @Size(max=30, message="status must have between {min} and {max} characters.") String status;
        @Nullable @Size(max=30, message="hair must have between {min} and {max} characters.") String hair;
        @Nullable @Size(max=30, message="eyes must have between {min} and {max} characters.") String eyes;
        @Nullable @Size(max=30, message="skin must have between {min} and {max} characters.") String skin;
        @Nullable @Size(max=50, message="gender must have between {min} and {max} characters.") String gender;
        @Nullable @Size(max=500, message="biography must not be longer than {max} characters.") String biography;
}
