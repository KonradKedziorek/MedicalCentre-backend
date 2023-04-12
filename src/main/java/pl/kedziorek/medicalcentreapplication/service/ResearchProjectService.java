package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;

public interface ResearchProjectService {
    ResearchProject saveResearchProject(ResearchProjectRequest researchProjectRequest);
}
