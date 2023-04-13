package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.Commission;

import java.util.Optional;
import java.util.UUID;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
    Optional<Commission> findByUuid(UUID uuid);
}
