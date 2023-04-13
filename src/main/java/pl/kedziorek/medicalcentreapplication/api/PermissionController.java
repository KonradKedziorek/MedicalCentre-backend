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
import java.util.UUID;

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

    @DeleteMapping("/permission/uuid={uuid}/delete")
    public ResponseEntity<?> deletePermission (@PathVariable UUID uuid) throws IOException {
        return ResponseEntity.ok().body(permissionService.deletePermission(uuid));
    }

}
