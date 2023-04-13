package pl.kedziorek.medicalcentreapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kedziorek.medicalcentreapplication.domain.dto.CommissionRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "commission", schema = "public")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private String typeOfResearch;

    private String description;

    private LocalDateTime dateOfResearch;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "research_project_id")
    @JsonIgnore
    private ResearchProject researchProject;

    public static Commission map(CommissionRequest commissionRequest, User user, ResearchProject researchProject) {
        return Commission.builder()
                .uuid(Objects.equals(commissionRequest.getUuid(), "") ? UUID.randomUUID() : UUID.fromString(commissionRequest.getUuid()))
                .typeOfResearch(commissionRequest.getTypeOfResearch())
                .description(commissionRequest.getDescription())
                .dateOfResearch(LocalDateTime.of(
                                LocalDate.parse(commissionRequest.getDateOfResearch()),
                                LocalTime.parse(commissionRequest.getTimeOfResearch())
                        )
                )
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdAt(LocalDateTime.now())
                .user(user)
                .researchProject(researchProject)
                .deleted(Boolean.FALSE)
                .build();
    }
}
