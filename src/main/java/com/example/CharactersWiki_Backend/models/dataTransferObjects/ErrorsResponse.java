package com.example.CharactersWiki_Backend.models.dataTransferObjects;

import java.util.List;

public record ErrorsResponse(List<String> messages) {}