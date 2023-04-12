package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;

import java.util.Optional;
import java.util.UUID;

public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Long> {
    Optional<ResearchProject> findByUuid(UUID uuid);
}
