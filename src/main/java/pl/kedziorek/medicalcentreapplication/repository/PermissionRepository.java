package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{}
