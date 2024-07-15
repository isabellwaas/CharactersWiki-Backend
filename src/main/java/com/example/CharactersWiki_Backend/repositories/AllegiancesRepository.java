package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Allegiance;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AllegiancesRepository extends JpaRepository<Allegiance, Integer>
{
    Optional<AllegianceResponse> getAllegianceById(int id);
    List<Allegiance> findAllegiancesByIdIn(List<Integer> ids);
}
