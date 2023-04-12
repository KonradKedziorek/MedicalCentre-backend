package pl.kedziorek.medicalcentreapplication.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;
import pl.kedziorek.medicalcentreapplication.service.ResearchProjectService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ResearchProjectController {
    private final ResearchProjectService researchProjectService;

    @PostMapping("/researchProject/save")
    public ResponseEntity<?> saveResearchProject(@Valid @RequestBody ResearchProjectRequest researchProjectRequest) {
        return ResponseEntity.ok().body(researchProjectService.saveResearchProject(researchProjectRequest));
    }
}
