package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Origin;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OriginsRepository extends JpaRepository<Origin, Integer>
{
    Optional<OriginResponse> findOriginById(int id);
}
