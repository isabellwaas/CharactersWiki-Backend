package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Integer> {}