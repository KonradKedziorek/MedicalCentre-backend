package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.domain.Commission;
import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;
import pl.kedziorek.medicalcentreapplication.domain.User;
import pl.kedziorek.medicalcentreapplication.domain.dto.CommissionRequest;
import pl.kedziorek.medicalcentreapplication.repository.CommissionRepository;
import pl.kedziorek.medicalcentreapplication.repository.ResearchProjectRepository;
import pl.kedziorek.medicalcentreapplication.repository.UserRepository;
import pl.kedziorek.medicalcentreapplication.service.CommissionService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommissionServiceImpl implements CommissionService {
    private final UserRepository userRepository;
    private final CommissionRepository commissionRepository;
    private final ResearchProjectRepository researchProjectRepository;
    private static final String INFO = "Commission not found in database!";

    @Override
    public Commission getCommission(UUID uuid) {
        return commissionRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException(INFO)
        );
    }

    @Valid
    @Override
    public Commission saveCommission(CommissionRequest commissionRequest) {
        log.info("Saving new commission to the database");
        User user = userRepository.findByPesel(commissionRequest.getUserPesel()).orElseThrow(() ->
                new ResourceNotFoundException("User not found in database")
        );
        ResearchProject researchProject = researchProjectRepository.findByUuid(
                UUID.fromString(commissionRequest.getResearchProjectUuid())
        ).orElseThrow(() ->
                new ResourceNotFoundException("Research project not found in database")
        );
        Commission commission = Commission.map(commissionRequest, user, researchProject);
        User doctor = userRepository.findByEmail(commission.getCreatedBy()).orElseThrow(() ->
                new ResourceNotFoundException("User does not exists!")
        );
        commission.setCreatedBy(doctor.getName() + " " + doctor.getSurname() + " (id: " + doctor.getId() + ")");
        commission.setDeleted(Boolean.FALSE);
        commissionRepository.save(commission);
        researchProject.getCommissions().add(commission);
        return commission;
    }

    @Override
    public Commission deleteCommission(UUID uuid) {
        Commission commission = commissionRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException(INFO)
        );
        if (commission.getDeleted() == Boolean.TRUE) {
            throw new ResourceNotFoundException(INFO);
        }
        commission.setDeleted(Boolean.TRUE);

        commission.getUser().getCommissions().remove(commission);
        commission.setUser(null);
        commission.getResearchProject().getCommissions().remove(commission);
        commission.setResearchProject(null);

        return commission;
    }
}
