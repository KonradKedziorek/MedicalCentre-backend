package pl.kedziorek.medicalcentreapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kedziorek.medicalcentreapplication.domain.dto.CommissionRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public static Commission map(CommissionRequest commissionRequest, User user) {
        return Commission.builder()
                .uuid(Objects.equals(commissionRequest.getUuid(), "") ? UUID.randomUUID() : UUID.fromString(commissionRequest.getUuid()))
                .typeOfResearch(commissionRequest.getTypeOfResearch())
                .description(commissionRequest.getDescription())
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .createdAt(LocalDateTime.now())
                .user(user)
                .deleted(Boolean.FALSE)
                .build();
    }
}