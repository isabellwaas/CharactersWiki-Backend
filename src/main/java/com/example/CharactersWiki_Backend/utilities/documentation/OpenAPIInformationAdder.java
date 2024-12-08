package com.example.CharactersWiki_Backend.utilities.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    servers = @Server(url = "http://localhost:8080"),
    info = @Info(
        title = "CharactersWiki",
        description = "CharactersWiki allows authors and fans to manage detailed character profiles, including relationships, allegiances, quotes, weapons, origins, locations, and securely uploaded images, serving as a comprehensive tool for storytelling and creative projects."
        )
    )
public class OpenAPIInformationAdder { }


