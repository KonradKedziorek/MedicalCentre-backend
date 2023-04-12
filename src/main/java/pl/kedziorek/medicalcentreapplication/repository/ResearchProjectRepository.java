package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;

public interface ResearchProjectRepository extends JpaRepository<ResearchProject, Long> {
}
