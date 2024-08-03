package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Weapon;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponsRepository extends JpaRepository<Weapon, Integer>
{
    Page<WeaponSummary> findWeaponsByNameContaining(String query, Pageable pageable);
    Optional<WeaponResponse> findWeaponById(int id);
    List<Weapon> findWeaponsByIdIn(List<Integer> ids);
}