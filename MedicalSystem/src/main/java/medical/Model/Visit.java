package medical.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "VISITS")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "visit_date")
    private LocalDateTime visitDate;

    public Visit() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public Visit(Long id, Patient patient, LocalDateTime visitDate) {
        this.id = id;
        this.patient = patient;
        this.visitDate = visitDate;
    }
}
