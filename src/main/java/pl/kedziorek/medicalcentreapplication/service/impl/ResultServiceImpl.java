package pl.kedziorek.medicalcentreapplication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kedziorek.medicalcentreapplication.config.exception.ResourceNotFoundException;
import pl.kedziorek.medicalcentreapplication.domain.Commission;
import pl.kedziorek.medicalcentreapplication.domain.Result;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResultRequest;
import pl.kedziorek.medicalcentreapplication.repository.CommissionRepository;
import pl.kedziorek.medicalcentreapplication.repository.ResultRepository;
import pl.kedziorek.medicalcentreapplication.service.ResultService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResultServiceImpl implements ResultService {
    private final ResultRepository resultRepository;
    private final CommissionRepository commissionRepository;
    private static final String INFO = "Result not found in database!";

    @Override
    public Result getResult(UUID uuid) {
        Result result = resultRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException(INFO));
        if (result.getDeleted() == Boolean.TRUE) {
            throw new ResourceNotFoundException(INFO);
        }
        return result;
    }

    @Override
    public Result saveOrUpdateResult(ResultRequest resultRequest) {
        // if uuid is null should create new object
        if (Objects.equals(resultRequest.getUuid(), "")) {
            log.info("Saving new result to the database");
            return saveResult(resultRequest);
        }// else update existing object
        return editResult(resultRequest);
    }

    @Valid
    public Result saveResult(ResultRequest resultRequest) {
        log.info("Saving new result to the database");
        Commission commission = commissionRepository.findByUuid(UUID.fromString(
                resultRequest.getCommissionUuid())
        ).orElseThrow(() ->
                new ResourceNotFoundException("Commission not found in database")
        );

        Result result = Result.map(resultRequest, commission);
        resultRepository.save(result);
        return result;
    }

    private Result editResult(ResultRequest resultRequest) {
        Result result = resultRepository.findByUuid(UUID.fromString(resultRequest.getUuid()))
                .orElseThrow(() -> new ResourceNotFoundException(INFO));
        var resultRef = changePropertiesValue(resultRequest, result);
        return resultRepository.save(resultRef);
    }

    private Result changePropertiesValue(ResultRequest resultRequest, Result result) {
        result.setResultDescription(resultRequest.getResultDescription());
        result.setModifiedAt(LocalDateTime.now());
        result.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        return result;
    }

    @Override
    public Result deleteResult(UUID uuid) {
        Result result = resultRepository.findByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException(INFO)
        );
        if (result.getDeleted() == Boolean.TRUE) {
            throw new ResourceNotFoundException(INFO);
        }
        result.setDeleted(Boolean.TRUE);
        result.getCommission().setDeleted(Boolean.TRUE);
        result.getCommission().setUser(null);

        return result;
    }
}
