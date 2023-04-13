package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.Result;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResultRequest;

import java.util.UUID;

public interface ResultService {
    Result getResult(UUID uuid);
    Result saveOrUpdateResult(ResultRequest resultRequest);
    Result deleteResult(UUID uuid);
}
