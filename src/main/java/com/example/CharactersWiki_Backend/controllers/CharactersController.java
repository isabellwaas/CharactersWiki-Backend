package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.AllegianceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.CharacterResponse;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.WeaponResponse;
import com.example.CharactersWiki_Backend.services.ICharactersService;
import com.example.CharactersWiki_Backend.utilities.Quality;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import com.example.CharactersWiki_Backend.utilities.documentation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("v1/characters")
@Validated
@Tag(name = "Characters")
public class CharactersController
{
    private final ICharactersService charactersService;

    @Autowired
    public CharactersController(ICharactersService charactersService)
    {
        this.charactersService = charactersService;
    }

    @Operation(description = "Get all characters.")
    @CommonApiResponses @OkApiResponse
    @GetMapping("")
    public ResponseEntity<EntityModel<CharactersResponse>> getCharacters(@RequestParam(required = false) @Size(min=1, max=30, message="query must have between {min} and {max} characters.") String query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam(required = false) SortDirection sortDirection)
    {
        CharactersResponse charactersResponse=charactersService.getCharacters(query==null?Optional.empty():Optional.of(query), pageNumber, perPage, sortDirection==null?Optional.empty():Optional.of(sortDirection));
        EntityModel<CharactersResponse> entityModel=EntityModel.of(charactersResponse);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(query, pageNumber, perPage, sortDirection)).withSelfRel().withType("GET"));
        if(pageNumber>1 && charactersResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(query, 1, perPage, sortDirection)).withRel("first").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(query, pageNumber-1, perPage, sortDirection)).withRel("prev").withType("GET"));
        }
        if(pageNumber<charactersResponse.maximumPage() && charactersResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(query, pageNumber+1, perPage, sortDirection)).withRel("next").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(query, charactersResponse.maximumPage(), perPage, sortDirection)).withRel("last").withType("GET"));
        }
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get all allegiances.")
    @CommonApiResponses @OkApiResponse
    @GetMapping("/allegiances")
    public ResponseEntity<EntityModel<AllegiancesResponse>> getAllegiances(@RequestParam(required = false) @Size(min=1, max=30, message="query must have between {min} and {max} characters.") String query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam(required = false) SortDirection sortDirection)
    {
        AllegiancesResponse allegiancesResponse=charactersService.getAllegiances(query==null?Optional.empty():Optional.of(query), pageNumber, perPage, sortDirection==null?Optional.empty():Optional.of(sortDirection));
        EntityModel<AllegiancesResponse> entityModel=EntityModel.of(allegiancesResponse);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(query, pageNumber, perPage, sortDirection)).withSelfRel().withType("GET"));
        if(pageNumber>1 && allegiancesResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(query, 1, perPage, sortDirection)).withRel("first").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(query, pageNumber-1, perPage, sortDirection)).withRel("prev").withType("GET"));
        }
        if(pageNumber<allegiancesResponse.maximumPage() && allegiancesResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(query, pageNumber+1, perPage, sortDirection)).withRel("next").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(query, allegiancesResponse.maximumPage(), perPage, sortDirection)).withRel("last").withType("GET"));
        }
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get all weapons.")
    @CommonApiResponses @OkApiResponse
    @GetMapping("/weapons")
    public ResponseEntity<EntityModel<WeaponsResponse>> getWeapons(@RequestParam(required = false) @Size(min=1, max=30, message="query must have between {min} and {max} characters.") String query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam(required = false) SortDirection sortDirection)
    {
        WeaponsResponse weaponsResponse=charactersService.getWeapons(query==null?Optional.empty():Optional.of(query), pageNumber, perPage, sortDirection==null?Optional.empty():Optional.of(sortDirection));
        EntityModel<WeaponsResponse> entityModel=EntityModel.of(weaponsResponse);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(query, pageNumber, perPage, sortDirection)).withSelfRel().withType("GET"));
        if(pageNumber>1 && weaponsResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(query, 1, perPage, sortDirection)).withRel("first").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(query, pageNumber-1, perPage, sortDirection)).withRel("prev").withType("GET"));
        }
        if(pageNumber<weaponsResponse.maximumPage() && weaponsResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(query, pageNumber+1, perPage, sortDirection)).withRel("next").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(query, weaponsResponse.maximumPage(), perPage, sortDirection)).withRel("last").withType("GET"));
        }
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get all quotes.")
    @CommonApiResponses @OkApiResponse
    @GetMapping("/quotes")
    public ResponseEntity<EntityModel<QuotesResponse>> getQuotes(@RequestParam(required = false) @Size(min=1, max=30, message="query must have between {min} and {max} characters.") String query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam(required = false) SortDirection sortDirection)
    {
        QuotesResponse quotesResponse=charactersService.getQuotes(query==null?Optional.empty():Optional.of(query), pageNumber, perPage, sortDirection==null?Optional.empty():Optional.of(sortDirection));
        EntityModel<QuotesResponse> entityModel=EntityModel.of(quotesResponse);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(query, pageNumber, perPage, sortDirection)).withSelfRel().withType("GET"));
        if(pageNumber>1 && quotesResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(query, 1, perPage, sortDirection)).withRel("first").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(query, pageNumber-1, perPage, sortDirection)).withRel("prev").withType("GET"));
        }
        if(pageNumber<quotesResponse.maximumPage() && quotesResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(query, pageNumber+1, perPage, sortDirection)).withRel("next").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(query, quotesResponse.maximumPage(), perPage, sortDirection)).withRel("last").withType("GET"));
        }
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get details of a specific character.")
    @CommonApiResponses @OkApiResponse @NotFoundApiResponse
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CharacterResponse>> getCharacter(@PathVariable int id) throws NotFoundException
    {
        EntityModel<CharacterResponse> entityModel=EntityModel.of(charactersService.getCharacterById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacter(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateCharacter(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteCharacter(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getCharacters(null, 1, 10, null)).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get the image of a specific character.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
    )
    @CommonApiResponses @NotFoundApiResponse
    @GetMapping("{id}/images")
    public ResponseEntity<byte[]> getImageOfCharacter(@PathVariable int id, @RequestParam Quality quality) throws Exception
    {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(charactersService.getImageOfCharacter(id, quality));
    }

    @Operation(description = "Get details of a specific allegiance.")
    @CommonApiResponses @OkApiResponse @NotFoundApiResponse
    @GetMapping("/allegiances/{id}")
    public ResponseEntity<EntityModel<AllegianceResponse>> getAllegiance(@PathVariable int id) throws NotFoundException
    {
        EntityModel<AllegianceResponse> entityModel=EntityModel.of(charactersService.getAllegianceById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiance(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateAllegiance(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteAllegiance(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getAllegiances(null, 1, 10, null)).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get details of a specific weapon.")
    @CommonApiResponses @OkApiResponse @NotFoundApiResponse
    @GetMapping("/weapons/{id}")
    public ResponseEntity<EntityModel<WeaponResponse>> getWeapon(@PathVariable int id) throws NotFoundException
    {
        EntityModel<WeaponResponse> entityModel=EntityModel.of(charactersService.getWeaponById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapon(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateWeapon(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteWeapon(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getWeapons(null, 1, 10, null)).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get details of a specific quote.")
    @CommonApiResponses @OkApiResponse @NotFoundApiResponse
    @GetMapping("/quotes/{id}")
    public ResponseEntity<EntityModel<QuoteResponse>> getQuote(@PathVariable int id) throws NotFoundException
    {
        EntityModel<QuoteResponse> entityModel=EntityModel.of(charactersService.getQuoteById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuote(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).updateQuote(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).deleteQuote(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CharactersController.class).getQuotes(null, 1, 10, null)).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Create a new character.")
    @CommonApiResponses @CreatedApiResponse
    @PostMapping("")
    public ResponseEntity<IdResponse> createCharacter(@RequestBody @Valid CreateCharacter createCharacter) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createCharacter(createCharacter));
    }

    @Operation(description = "Create an image for a specific character.")
    @CommonApiResponses @CreatedApiResponse @NotFoundApiResponse
    @PostMapping("{id}/images")
    public ResponseEntity<IdResponse> createImageForCharacter(@PathVariable int id, @RequestParam("image") MultipartFile image) throws Exception
    {
        return ResponseEntity.status(201).body(charactersService.createImageForCharacter(id, image));
    }

    @Operation(description = "Create a new allegiance.")
    @CommonApiResponses @CreatedApiResponse
    @PostMapping("/allegiances")
    public ResponseEntity<IdResponse> createAllegiance(@RequestBody @Valid CreateAllegiance createAllegiance) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createAllegiance(createAllegiance));
    }

    @Operation(description = "Create a new weapon.")
    @CommonApiResponses @CreatedApiResponse
    @PostMapping("/weapons")
    public ResponseEntity<IdResponse> createWeapon(@RequestBody @Valid CreateWeapon createWeapon) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createWeapon(createWeapon));
    }

    @Operation(description = "Create a new quote.")
    @CommonApiResponses @CreatedApiResponse
    @PostMapping("/quotes")
    public ResponseEntity<IdResponse> createQuote(@RequestBody @Valid CreateQuote createQuote) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.createQuote(createQuote));
    }

    @Operation(description = "Update a specific character.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCharacter(@PathVariable int id, @RequestBody @Valid UpdateCharacter updateCharacter) throws NotFoundException
    {
        charactersService.updateCharacter(id, updateCharacter);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Update a specific allegiance.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @PatchMapping("/allegiances/{id}")
    public ResponseEntity<Void> updateAllegiance(@PathVariable int id, @RequestBody @Valid UpdateAllegiance updateAllegiance) throws NotFoundException
    {
        charactersService.updateAllegiance(id, updateAllegiance);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Update a specific weapon.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @PatchMapping("/weapons/{id}")
    public ResponseEntity<Void> updateWeapon(@PathVariable int id, @RequestBody @Valid UpdateWeapon updateWeapon) throws NotFoundException
    {
        charactersService.updateWeapon(id, updateWeapon);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Update a specific quote.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @PatchMapping("/quotes/{id}")
    public ResponseEntity<IdResponse> updateQuote(@PathVariable int id, @RequestBody @Valid UpdateQuote updateQuote) throws NotFoundException
    {
        return ResponseEntity.status(201).body(charactersService.updateQuote(id, updateQuote));
    }

    @Operation(description = "Delete a specific character.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete the image of a specific character.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/{id}/images")
    public ResponseEntity<Void> deleteImageOfCharacter(@PathVariable int id) throws Exception
    {
        charactersService.deleteImageOfCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete a specific allegiance.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/allegiances/{id}")
    public ResponseEntity<Void> deleteAllegiance(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteAllegiance(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete a specific weapon.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/weapons/{id}")
    public ResponseEntity<Void> deleteWeapon(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteWeapon(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete a specific quote.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable int id) throws NotFoundException
    {
        charactersService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }
}
