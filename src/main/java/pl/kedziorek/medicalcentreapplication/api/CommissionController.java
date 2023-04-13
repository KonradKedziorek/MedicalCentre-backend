package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kedziorek.medicalcentreapplication.domain.Commission;
import pl.kedziorek.medicalcentreapplication.domain.dto.CommissionRequest;
import pl.kedziorek.medicalcentreapplication.service.CommissionService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommissionController {
    private final CommissionService commissionService;

    @PostMapping("/commission/save")
    public ResponseEntity<Commission> saveCommission(@Valid @RequestBody CommissionRequest commissionRequest) {
        return ResponseEntity.ok().body(commissionService.saveCommission(commissionRequest));
    }

    @PutMapping("/commission/uuid={uuid}/delete")
    public ResponseEntity<Commission> deleteCommission(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(commissionService.deleteCommission(uuid));
    }

    @GetMapping("/commission/uuid={uuid}/get")
    public ResponseEntity<Commission> getResearchProject(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(commissionService.getCommission(uuid));
    }
}
