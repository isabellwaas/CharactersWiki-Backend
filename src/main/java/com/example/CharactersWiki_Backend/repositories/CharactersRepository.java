package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharactersRepository extends JpaRepository<Character, Integer>
{
    Optional<CharacterResponse> getCharacterById(int id);
}
