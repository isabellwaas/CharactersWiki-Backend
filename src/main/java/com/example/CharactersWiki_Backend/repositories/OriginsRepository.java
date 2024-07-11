package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginsRepository extends JpaRepository<Origin, Integer> {}
