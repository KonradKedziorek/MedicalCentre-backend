package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kedziorek.medicalcentreapplication.domain.Result;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResultRequest;
import pl.kedziorek.medicalcentreapplication.service.ResultService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ResultController {
     private final ResultService resultService;
    @PostMapping("/result/saveOrUpdate")
    public ResponseEntity<Result> saveOrUpdateResult(@Valid @RequestBody ResultRequest resultRequest) {
        return ResponseEntity.ok().body(resultService.saveOrUpdateResult(resultRequest));
    }

    @PutMapping("/result/uuid={uuid}/delete")
    public ResponseEntity<Result> deleteResult(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(resultService.deleteResult(uuid));
    }

    @GetMapping("/result/uuid={uuid}/get")
    public ResponseEntity<Result> getResult(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(resultService.getResult(uuid));
    }
}
