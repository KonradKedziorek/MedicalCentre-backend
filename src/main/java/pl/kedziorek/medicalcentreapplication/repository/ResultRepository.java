package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.Result;

import java.util.Optional;
import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByUuid(UUID uuid);
}
