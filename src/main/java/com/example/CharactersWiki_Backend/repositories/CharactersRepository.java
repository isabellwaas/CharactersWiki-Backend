package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharactersRepository extends JpaRepository<Character, Integer> {}
