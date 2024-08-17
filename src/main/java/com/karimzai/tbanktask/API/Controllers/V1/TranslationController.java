package com.karimzai.tbanktask.API.Controllers.V1;

import com.karimzai.tbanktask.Application.Exceptions.ServiceUnavailableException;
import com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand.TranslateTextCommand;
import com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand.TranslateTextCommandResponse;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery.GetAllTranslationQuery;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery.GetTranslationQuery;
import com.karimzai.tbanktask.Application.Models.Translate.TranslateTextDto;
import com.karimzai.tbanktask.Application.Models.Translate.TranslationInfoDto;
import com.karimzai.tbanktask.Application.Services.TranslateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/translations")
@Tag(name = "Translations")
public class TranslationController {
    private final HttpServletRequest request;
    private final TranslateService translateService;

    public TranslationController(HttpServletRequest request, TranslateService translateService) {
        this.request = request;
        this.translateService = translateService;
    }

    @PostMapping
    @Operation(description = "Перевести текст с языка оригинала на язык перевода.", summary = "Перевести текст")
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Текст успешно переведен.", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
                    @ApiResponse(description = "Исходный язык и язык перевода одинаковы.", responseCode = "400",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(description = "Сервис перевода недоступна.", responseCode = "503",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TranslationInfoDto.class)))
            }
    )
    public ResponseEntity<?> translate(@RequestBody TranslateTextDto translationText) {
        var requestIPAddr = request.getRemoteAddr();
        var translateCommand = new TranslateTextCommand(translationText, requestIPAddr);
        TranslateTextCommandResponse response;

        try {
            response = translateService.translate(translateCommand);
        } catch (ServiceUnavailableException e) {
            return new ResponseEntity<>("Ошибка доступа к ресурсу перевода.", HttpStatus.SERVICE_UNAVAILABLE);
        }

        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getTranslatedText());
        } else {
            return ResponseEntity.badRequest().body(response.getValidationErrors());
        }
    }

    @GetMapping("/{translationId}")
    @Operation(description = "Получить переведенный текст по идентификатору.", summary = "Получить переведенный текст")
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Перевод с идентификатором найден.", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TranslationInfoDto.class))),
                    @ApiResponse(description = "Неверный идентификатор.", responseCode = "400",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(description = "Перевод с идентификатором не найден.", responseCode = "404")
            }
    )
    public ResponseEntity<?> getTranslation(@PathVariable String translationId) {
        var request = new GetTranslationQuery(translationId);
        var response = translateService.getTranslationInfo(request);

        if (response.isSuccess()) {
            if (response.getTranslation() == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response.getTranslation());
        } else {
            return ResponseEntity.badRequest().body(response.getValidationErrors());
        }
    }

    @GetMapping
    @Operation(description = "Получить список переведенных текст.", summary = "Получить список переведенных текст")
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Список переведенных текст.", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TranslationInfoDto.class))),
                    @ApiResponse(description = "Номер страницы должен быть больше нуля.", responseCode = "400",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            }
    )
    public ResponseEntity<?> getAllTranslations(@RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "false") boolean orderByCreation) {
        var getAllTranslationInfoQuery = new GetAllTranslationQuery(pageNumber, pageSize, orderByCreation);
        var response = translateService.getAllTranslationInfo(getAllTranslationInfoQuery);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getTranslations());
        } else {
            return ResponseEntity.badRequest().body(response.getValidationErrors());
        }
    }
}
