package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.domain.Address;
import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;
import pl.kedziorek.medicalcentreapplication.domain.Role;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;
import pl.kedziorek.medicalcentreapplication.domain.dto.UserRequest;
import pl.kedziorek.medicalcentreapplication.repository.ResearchProjectRepository;
import pl.kedziorek.medicalcentreapplication.service.ResearchProjectService;
import pl.kedziorek.medicalcentreapplication.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResearchProjectServiceImpl implements ResearchProjectService {
    private final UserService userService;
    private final ResearchProjectRepository researchProjectRepository;

    @Override
    @Valid
    public ResearchProject saveResearchProject(ResearchProjectRequest researchProjectRequest) {
        log.info("Saving new user to the database");
        Set<User> doctors = userService.getUsersByIdAndSurname(researchProjectRequest.getDoctors());

        ResearchProject researchProject = ResearchProject.map(researchProjectRequest, doctors);

        researchProjectRepository.save(researchProject);

        return researchProject;
    }
}
