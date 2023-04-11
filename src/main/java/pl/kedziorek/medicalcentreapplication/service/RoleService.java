package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRolesByNames(Set<String> names);
    Role saveRole(Role role);
}
