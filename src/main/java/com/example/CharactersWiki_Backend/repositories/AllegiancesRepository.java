package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Allegiance;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AllegiancesRepository extends JpaRepository<Allegiance, Integer>
{
    Page<AllegianceSummary> findAllegiancesByNameContainingOrNoteContaining(String nameQuery, String noteQuery, Pageable pageable);
    Optional<AllegianceResponse> findAllegianceById(int id);
    List<Allegiance> findAllegiancesByIdIn(List<Integer> ids);
}
