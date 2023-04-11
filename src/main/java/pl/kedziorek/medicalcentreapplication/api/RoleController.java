package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kedziorek.medicalcentreapplication.domain.Role;
import pl.kedziorek.medicalcentreapplication.domain.dto.ChangeUserRolesRequest;
import pl.kedziorek.medicalcentreapplication.domain.dto.RoleRequest;
import pl.kedziorek.medicalcentreapplication.service.RoleService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@Validated @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.ok().body(roleService.saveRole(roleRequest));
    }

    @PutMapping("/user/uuid={uuid}/role/changeUserRoles")
    public ResponseEntity<?> changeUserRoles(
            @RequestBody ChangeUserRolesRequest changeUserRolesRequest,
            @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(roleService.changeUserRoles(changeUserRolesRequest, uuid));
    }
}
