package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;

import java.util.UUID;

public interface ResearchProjectService {
    ResearchProject saveOfUpdateResearchProject(ResearchProjectRequest researchProjectRequest);
    ResearchProject deleteResearchProject(UUID uuid);
}
