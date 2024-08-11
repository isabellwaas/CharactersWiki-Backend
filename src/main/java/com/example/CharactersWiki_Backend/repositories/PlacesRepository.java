package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Place;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterSummary;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlacesRepository extends JpaRepository<Place, Integer>
{
    Page<PlaceSummary> findPlacesByNameContainingOrTypeContaining(String nameQuery, String typeQuery, Pageable pageable);
    Optional<PlaceResponse> findPlaceById(int id);
    List<Place> findPlacesByIdIn(List<Integer> ids);
}