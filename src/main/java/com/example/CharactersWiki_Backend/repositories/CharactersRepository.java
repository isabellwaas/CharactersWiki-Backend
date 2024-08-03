package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Character;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharactersRepository extends JpaRepository<Character, Integer>
{
    Page<CharacterSummary> findCharactersByFirstnameContainingOrLastnameContainingOrNicknameContainingOrTitleContaining(String firstnameQuery, String lastnameQuery, String nicknameQuery, String titleQuery, Pageable pageable);
    Optional<CharacterResponse> findCharacterById(int id);
    List<Character> findCharactersByIdIn(List<Integer> ids);
}
