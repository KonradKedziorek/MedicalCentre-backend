package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kedziorek.medicalcentreapplication.domain.dto.PermissionRequest;
import pl.kedziorek.medicalcentreapplication.service.PermissionService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping(value = "/permission/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> savePermission(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @Valid @RequestPart PermissionRequest permissionRequest
            ) throws IOException {
        return ResponseEntity.ok().body(permissionService.savePermission(multipartFile, permissionRequest));
    }

/*
    @PostMapping(value = "/addRecipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestPart Recipe recipe, //TODO: do przemyślenia
                                            @RequestParam(value = "file", required = false) MultipartFile[] imagesBytes){
        return ResponseEntity.ok().body(recipeService.addRecipe(recipe, imagesBytes));
    }
*/

}
