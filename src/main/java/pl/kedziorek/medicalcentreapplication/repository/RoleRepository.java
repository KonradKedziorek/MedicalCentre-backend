package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Boolean existsByName(String name);
    Set<Role> findRolesByNameIn(Set<String> roles);
}
