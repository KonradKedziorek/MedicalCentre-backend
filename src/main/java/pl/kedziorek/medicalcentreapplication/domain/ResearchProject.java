package pl.kedziorek.medicalcentreapplication.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "research_project", schema = "public")
public class ResearchProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> patients = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> doctors = new HashSet<>();

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "researchProject")
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(mappedBy = "researchProject")
    private List<Commission> commissions = new ArrayList<>();

    public static ResearchProject map(ResearchProjectRequest researchProjectRequest, Set<User> doctors) {
        return ResearchProject.builder()
                .uuid(Objects.equals(researchProjectRequest.getUuid(), "") ? UUID.randomUUID() : UUID.fromString(researchProjectRequest.getUuid()))
                .name(researchProjectRequest.getName())
                .description(researchProjectRequest.getDescription())
                .doctors(doctors)
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdAt(LocalDateTime.now())
                .deleted(Boolean.FALSE)
                .build();
    }
}
