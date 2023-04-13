package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;
import pl.kedziorek.medicalcentreapplication.repository.ResearchProjectRepository;
import pl.kedziorek.medicalcentreapplication.service.ResearchProjectService;
import pl.kedziorek.medicalcentreapplication.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResearchProjectServiceImpl implements ResearchProjectService {
    private final UserService userService;
    private final ResearchProjectRepository researchProjectRepository;

    @Override
    public ResearchProject getResearchProject(UUID uuid) {
        return researchProjectRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Ni mo"));
    }

    @Override
    public ResearchProject saveOfUpdateResearchProject(ResearchProjectRequest researchProjectRequest) {
        // if uuid is null should create new object
        if (Objects.equals(researchProjectRequest.getUuid(), "")) {
            log.info("Saving new research project to the database");
            return saveResearchProject(researchProjectRequest);
        }// else update existing object
        return editResearchProject(researchProjectRequest);
    }

    @Valid
    public ResearchProject saveResearchProject(ResearchProjectRequest researchProjectRequest) {
        log.info("Saving new research project to the database");
        Set<User> doctors = userService.getUsersByIdAndSurname(researchProjectRequest.getDoctors());
        ResearchProject researchProject = ResearchProject.map(researchProjectRequest, doctors);
        researchProjectRepository.save(researchProject);
        return researchProject;
    }

    private ResearchProject editResearchProject(ResearchProjectRequest researchProjectRequest) {
        ResearchProject researchProject = researchProjectRepository.findByUuid(UUID.fromString(researchProjectRequest.getUuid()))
                .orElseThrow(() -> new ResourceNotFoundException("Research project does not exist!"));
        var researchProjectRef = changePropertiesValue(researchProjectRequest, researchProject);
        return researchProjectRepository.save(researchProjectRef);
    }

    private ResearchProject changePropertiesValue(ResearchProjectRequest researchProjectRequest, ResearchProject researchProject) {
        researchProject.setName(researchProjectRequest.getName());
        researchProject.setDescription(researchProjectRequest.getDescription());
        researchProject.setDoctors(userService.getUsersByIdAndSurname(researchProjectRequest.getDoctors()));
        researchProject.setModifiedAt(LocalDateTime.now());
        researchProject.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return researchProject;
    }

    @Override
    public ResearchProject deleteResearchProject(UUID uuid) {
        ResearchProject researchProject = researchProjectRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Research project not found in database!")
        );
        if (researchProject.getDeleted() == Boolean.TRUE) {
            throw new ResourceNotFoundException("Research project not found in database!");
        }
        researchProject.setDeleted(Boolean.TRUE);

        researchProject.setDoctors(Collections.emptySet());
        researchProject.setPatients(Collections.emptySet());

        return researchProject;
    }
}
