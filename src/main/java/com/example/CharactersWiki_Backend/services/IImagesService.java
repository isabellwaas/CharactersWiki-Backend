package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.Image;
import com.example.CharactersWiki_Backend.utilities.Quality;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IImagesService
{
    public Image save(MultipartFile file) throws Exception;
    public Optional<byte[]> get(int id, Quality quality) throws Exception;
    public void delete(int id) throws Exception;
}