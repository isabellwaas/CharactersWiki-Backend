package com.example.CharactersWiki_Backend.repositories;

import com.example.CharactersWiki_Backend.models.Weapon;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponsRepository extends JpaRepository<Weapon, Integer>
{
    Optional<WeaponResponse> getWeaponById(int id);
    List<Weapon> findWeaponsByIdIn(List<Integer> ids);
}