package com.example.CharactersWiki_Backend.controllers;

import com.example.CharactersWiki_Backend.models.dataTransferObjects.*;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.OriginResponse;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.PlaceResponse;
import com.example.CharactersWiki_Backend.models.projectionInterfaces.QuoteResponse;
import com.example.CharactersWiki_Backend.services.IOriginsService;
import com.example.CharactersWiki_Backend.utilities.SortDirection;
import com.example.CharactersWiki_Backend.utilities.documentation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/origins")
@Validated
@Tag(name = "Origins")
public class OriginsController
{
    private final IOriginsService originsService;

    @Autowired
    public OriginsController(IOriginsService originsService)
    {
        this.originsService = originsService;
    }

    @Operation(description = "Get all origins.")
    @CommonApiResponses @OkApiResponse
    @GetMapping("")
    public ResponseEntity<EntityModel<OriginsResponse>> getOrigins(@RequestParam(required = false) @Size(min=1, max=30, message="query must have between {min} and {max} characters.") String query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam(required = false) SortDirection sortDirection)
    {
        OriginsResponse originsResponse=originsService.getOrigins(query==null?Optional.empty():Optional.of(query), pageNumber, perPage, sortDirection==null?Optional.empty():Optional.of(sortDirection));
        EntityModel<OriginsResponse> entityModel=EntityModel.of(originsResponse);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigins(query, pageNumber, perPage, sortDirection)).withSelfRel().withType("GET"));
        if(pageNumber>1 && originsResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigins(query, 1, perPage, sortDirection)).withRel("first").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigins(query, pageNumber-1, perPage, sortDirection)).withRel("prev").withType("GET"));
        }
        if(pageNumber<originsResponse.maximumPage() && originsResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigins(query, pageNumber+1, perPage, sortDirection)).withRel("next").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigins(query, originsResponse.maximumPage(), perPage, sortDirection)).withRel("last").withType("GET"));
        }
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get all places.")
    @CommonApiResponses @OkApiResponse
    @GetMapping("/places")
    public ResponseEntity<EntityModel<PlacesResponse>> getPlaces(@RequestParam(required = false) @Size(min=1, max=30, message="query must have between {min} and {max} characters.") String query, @RequestParam @Min(value=1, message="pageNumber must be at least 1.") int pageNumber, @RequestParam @Min(value=1, message="perPage must be at least 1.") @Max(value=50, message="perPage must be at most 50.") int perPage, @RequestParam(required = false) SortDirection sortDirection)
    {
        PlacesResponse placesResponse=originsService.getPlaces(query==null?Optional.empty():Optional.of(query), pageNumber, perPage, sortDirection==null?Optional.empty():Optional.of(sortDirection));
        EntityModel<PlacesResponse> entityModel=EntityModel.of(placesResponse);
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlaces(query, pageNumber, perPage, sortDirection)).withSelfRel().withType("GET"));
        if(pageNumber>1 && placesResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlaces(query, 1, perPage, sortDirection)).withRel("first").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlaces(query, pageNumber-1, perPage, sortDirection)).withRel("prev").withType("GET"));
        }
        if(pageNumber<placesResponse.maximumPage() && placesResponse.maximumPage()>0)
        {
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlaces(query, pageNumber+1, perPage, sortDirection)).withRel("next").withType("GET"));
            entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlaces(query, placesResponse.maximumPage(), perPage, sortDirection)).withRel("last").withType("GET"));
        }
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get details of a specific origin.")
    @CommonApiResponses @OkApiResponse @NotFoundApiResponse
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OriginResponse>> getOrigin(@PathVariable int id) throws NotFoundException
    {
        EntityModel<OriginResponse> entityModel=EntityModel.of(originsService.getOriginById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigin(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).updateOrigin(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).deleteOrigin(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getOrigins(null, 1, 10, null)).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Get details of a specific place.")
    @CommonApiResponses @OkApiResponse @NotFoundApiResponse
    @GetMapping("/places/{id}")
    public ResponseEntity<EntityModel<PlaceResponse>> getPlace(@PathVariable int id) throws NotFoundException
    {
        EntityModel<PlaceResponse> entityModel=EntityModel.of(originsService.getPlaceById(id));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlace(id)).withSelfRel().withType("GET"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).updatePlace(id, null)).withRel("update").withType("PATCH"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).deletePlace(id)).withRel("delete").withType("DELETE"));
        entityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OriginsController.class).getPlaces(null, 1, 10, null)).withRel("all").withType("GET"));
        return ResponseEntity.ok(entityModel);
    }

    @Operation(description = "Create a new origin.")
    @CommonApiResponses @CreatedApiResponse
    @PostMapping("")
    public ResponseEntity<IdResponse> createOrigin(@RequestBody @Valid CreateOrigin createOrigin) throws NotFoundException
    {
        return ResponseEntity.status(201).body(originsService.createOrigin(createOrigin));
    }

    @Operation(description = "Create a new place.")
    @CommonApiResponses @CreatedApiResponse
    @PostMapping("/places")
    public ResponseEntity<IdResponse> createPlace(@RequestBody @Valid CreatePlace createPlace) throws NotFoundException
    {
        return ResponseEntity.status(201).body(originsService.createPlace(createPlace));
    }

    @Operation(description = "Update a specific origin.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateOrigin(@PathVariable int id, @RequestBody @Valid UpdateOrigin updateOrigin) throws NotFoundException
    {
        originsService.updateOrigin(id, updateOrigin);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Update a specific place.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @PatchMapping("/places/{id}")
    public ResponseEntity<Void> updatePlace(@PathVariable int id, @RequestBody @Valid UpdatePlace updatePlace) throws NotFoundException
    {
        originsService.updatePlace(id, updatePlace);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete a specific origin.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrigin(@PathVariable int id) throws NotFoundException
    {
        originsService.deleteOrigin(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete a specific place.")
    @CommonApiResponses @NoContentApiResponse @NotFoundApiResponse
    @DeleteMapping("/places/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable int id) throws NotFoundException
    {
        originsService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}