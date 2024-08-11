package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Origin;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginsRepository extends JpaRepository<Origin, Integer>
{
    Page<OriginSummary> findOriginsByNameContaining(String query, Pageable pageable);
    Optional<OriginResponse> findOriginById(int id);
}
