package pl.kedziorek.medicalcentreapplication.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResearchProjectRequest;
import pl.kedziorek.medicalcentreapplication.domain.dto.ResultRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "result", schema = "public")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private String resultDescription;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private Boolean deleted = Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commission_id", referencedColumnName = "id")
    private Commission commission;

    public static Result map(ResultRequest resultRequest, Commission commission) {
        return Result.builder()
                .uuid(Objects.equals(resultRequest.getUuid(), "") ? UUID.randomUUID() : UUID.fromString(resultRequest.getUuid()))
                .resultDescription(resultRequest.getResultDescription())
                .commission(commission)
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdAt(LocalDateTime.now())
                .deleted(Boolean.FALSE)
                .build();
    }
}
