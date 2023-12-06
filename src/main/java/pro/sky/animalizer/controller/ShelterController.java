package pro.sky.animalizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalizer.model.Shelter;
import pro.sky.animalizer.service.ShelterService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService service;

    private static final Logger logger = LoggerFactory.getLogger(ShelterService.class);

    public ShelterController(ShelterService service) {
        this.service = service;
    }

    @Operation(
            summary = "Поиск всех приютов, находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех приютов, существующих в базе данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter[].class))
                            )
                            }
                    )
            })
    @GetMapping("/shelters")
    public Page<Shelter> getAllShelters(@RequestParam Pageable pageable) {
        return (Page<Shelter>) service.getAllShelters(pageable);
    }

    @Operation(
            summary = "Поиск приюта по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют, найденный по идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Приюта с переданным id не существует"
                    )
            })
    @GetMapping("/{id}")
    public Shelter getShelterById(@Parameter(description = "Идентификатор для поиска") @PathVariable("id") Long id) {
        return service.getShelterById(id);
    }

    @Operation(
            summary = "Добавление приюта в базу данных",
            requestBody = @RequestBody(
                    description = "Добавляемый приют",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют добавлен в базу данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                            }
                    )
            })
    @PostMapping
    public Shelter createShelter(@RequestBody Shelter shelter) {
        logger.info("Received Shelter data: {}", shelter);
        return service.createShelter(shelter);
    }

    @Operation(
            summary = "Изменение приюта в базе данных по искомому идентификатору",
            requestBody = @RequestBody(
                    description = "Отредактированный приют",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют с переданным илентификатором изменен",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Приюта с переданным id не существует"
                    )
            })
    @PutMapping("/{id}")
    public Shelter editShelter(@Parameter(description = "Идентификатор для поиска") @PathVariable("id") Long id, @RequestBody Shelter shelter) {
        return service.editShelter(id, shelter);
    }

    @Operation(
            summary = "Удаление приюта по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют с переданным илентификатором удален",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Приюта с переданным id не существует"
                    )
            })
    @DeleteMapping("/{id}")
    public void deleteShelter(@Parameter(description = "Идентификатор для поиска") @PathVariable("id") Long id) {
        service.deleteShelter(id);
    }
}
