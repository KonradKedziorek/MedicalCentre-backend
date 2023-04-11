package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.Role;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.ChangeUserRolesRequest;
import pl.kedziorek.medicalcentreapplication.domain.dto.RoleRequest;

import java.util.Set;
import java.util.UUID;

public interface RoleService {
    Set<Role> getRolesByNames(Set<String> names);
    Role saveRole(RoleRequest roleRequest);
    User changeUserRoles(ChangeUserRolesRequest changeUserRolesRequest, UUID uuid);
}
