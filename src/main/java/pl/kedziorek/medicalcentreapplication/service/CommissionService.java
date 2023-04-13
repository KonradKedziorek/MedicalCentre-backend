package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.Commission;
import pl.kedziorek.medicalcentreapplication.domain.dto.CommissionRequest;

import java.util.UUID;

public interface CommissionService {
    Commission getCommission(UUID uuid);
    Commission saveCommission(CommissionRequest commissionRequest);
    Commission deleteCommission(UUID uuid);
}
