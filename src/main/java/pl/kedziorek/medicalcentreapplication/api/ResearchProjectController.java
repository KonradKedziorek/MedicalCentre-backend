package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kedziorek.medicalcentreapplication.domain.ResearchProject;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;
import pl.kedziorek.medicalcentreapplication.service.ResearchProjectService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ResearchProjectController {
    private final ResearchProjectService researchProjectService;

    @PostMapping("/researchProject/saveOrUpdate")
    public ResponseEntity<?> saveResearchProject(@Valid @RequestBody ResearchProjectRequest researchProjectRequest) {
        return ResponseEntity.ok().body(researchProjectService.saveOfUpdateResearchProject(researchProjectRequest));
    }

    @PutMapping("/researchProject/uuid={uuid}/delete")
    public ResponseEntity<ResearchProject> deleteResearchProject(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok().body(researchProjectService.deleteResearchProject(uuid));
    }
}
