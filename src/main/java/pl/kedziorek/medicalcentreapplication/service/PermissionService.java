package pl.kedziorek.medicalcentreapplication.service;

import org.springframework.web.multipart.MultipartFile;
import pl.kedziorek.medicalcentreapplication.domain.Permission;
import pl.kedziorek.medicalcentreapplication.domain.dto.PermissionRequest;

import java.io.IOException;
import java.util.UUID;

public interface PermissionService {
    Permission savePermission(MultipartFile multipartFile, PermissionRequest permissionRequest) throws IOException;
    Permission deletePermission(UUID uuid) throws IOException;
}
