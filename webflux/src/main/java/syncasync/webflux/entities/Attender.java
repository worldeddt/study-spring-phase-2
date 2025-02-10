package syncasync.webflux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import syncasync.webflux.enums.AttenderStatus;

import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attender")
public class Attender extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attender_id")
    private Long id;
    @Column(name = "attender_number", length = 5, unique = true)
    private String attenderNumber;
    private String name;
    private String tel;
    @Enumerated(EnumType.STRING)
    private AttenderStatus status;

    @OneToMany(mappedBy = "attender")
    private List<LectureApplication> lectureApplications;
}