package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.domain.Permission;
import pl.kedziorek.medicalcentreapplication.domain.dto.PermissionRequest;
import pl.kedziorek.medicalcentreapplication.repository.PermissionRepository;
import pl.kedziorek.medicalcentreapplication.repository.ResearchProjectRepository;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;
import pl.kedziorek.medicalcentreapplication.service.PermissionService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final ResearchProjectRepository researchProjectRepository;

    @Value("${permissions.dir}")
    private String permissionsDir;

    @Override
    public Permission savePermission(MultipartFile multipartFile, PermissionRequest permissionRequest) throws IOException {
        log.info("Saving new permission to the database");

        Permission permission = new Permission();
        setStaticPropertiesOfSchedule(permission);

        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        permission.setName("PERMISSION_" + permission.getDate() + "." + extension);

        Path path = Paths.get(permissionsDir + File.separator + permission.getUuid() + "." + extension);

        permission.setPermissionDir(permissionsDir + "/" + permission.getUuid() + "." + extension);

        permission.setUser(userRepository.findByUuid(permissionRequest.getUserUuid()).orElseThrow(() ->
                new ResourceNotFoundException("User not found in database")));

        permission.setResearchProject(researchProjectRepository.findByUuid(permissionRequest.getResearchProjectUuid())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Research project not found in database"))
        );

        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


        userRepository.findByUuid(permissionRequest.getUserUuid())
                .ifPresentOrElse(user ->
                        user.getPermissions().add(permission),
                        () -> {
                            throw new ResourceNotFoundException("User with uuid " + permissionRequest.getUserUuid() + " not found");
                        }
                );

        researchProjectRepository.findByUuid(permissionRequest.getResearchProjectUuid())
                .ifPresent(project -> {
                    project.getPermissions().add(permission);
                    project.getPatients().add(userRepository.findByUuid(permissionRequest.getUserUuid())
                                    .orElseThrow(() ->
                                            new ResourceNotFoundException("User not found in database")
                                    )
                            );
                        }
                );

       return permissionRepository.save(permission);
    }

    private void setStaticPropertiesOfSchedule(Permission permission) {
        permission.setUuid(UUID.randomUUID());
        permission.setDate(LocalDateTime.now());
        permission.setCreatedAt(LocalDateTime.now());
        permission.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        permission.setDeleted(Boolean.FALSE);
    }
}
