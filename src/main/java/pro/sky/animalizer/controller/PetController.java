package pro.sky.animalizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(
            summary = "Поиск всех питомцев в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех питомцев, существующих в базе данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet[].class))
                            )
                            }
                    )
            })
    @GetMapping("/pets")
    public Page<Pet> getAllPets(@RequestParam Pageable pageable) {
        return (Page<Pet>) petService.getAllPet(pageable);
    }

    @Operation(
            summary = "Поиск питомца по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Питомец, найденный по идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Питомца с переданным id не существует"
                    )
            })
    @GetMapping("/id")
    public Pet findPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @Operation(
            summary = "Добавление питомца в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавляемый питомец",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Питомец добавлен в базу данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    )
            })
    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    @Operation(
            summary = "Изменение питомца в базе данных по искомому идентификатору",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отредактированный питомец",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Питомец с переданным илентификатором изменен",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Питомец с переданным id не существует"
                    )
            })
    @PutMapping("/{id}")
    public ResponseEntity<Pet> editPet(@Parameter(description = "Идентификатор для поиска") @PathVariable Long id,
                                       @RequestBody Pet pet) {
        Pet editedPet = petService.editPet(id, pet);
        if (editedPet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedPet);
    }

    @Operation(
            summary = "Удаление питомца по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Питомец с переданным илентификатором удален",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Питомец с переданным id не существует"
                    )
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> removePet(@Parameter(description = "Идентификатор для поиска") @PathVariable Long id) {
        Pet deletedPet = petService.deletePet(id);
        if (deletedPet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedPet);
    }
}
