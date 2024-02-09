package medical.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    @NotBlank(message = "Imię pacjenta jest wymagane")
    private String name;
    @Column(name="surname")
    @NotBlank(message = "Nazwisko pacjenta jest wymagane")
    private String surname;
    @Column(name="age")
    @NotNull(message = "Wiek pacjenta jest wymagany")
    private int age;
    @Column(name="description")
    @NotBlank(message = "Opis zdrowotny pacjenta jest wymagany")
    private String description;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Visit> visits;

    @Column(name = "weight", columnDefinition = "DOUBLE")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(name="plec")
    private Plec plec;

    @Column(name="ilnessHistory")
    private String ilnessHistory;

    @Column(name="chronicDiseasesAndDrugs")
    private String chronicDiseasesAndDrugs;

    @Column(name="vaccination")
    private String vaccination;

    @Column(name="contraindications")
    private String contraindications;

    public String getChronicDiseasesAndDrugs() {
        return chronicDiseasesAndDrugs;
    }

    public void setChronicDiseasesAndDrugs(String chronicDiseasesAndDrugs) {
        this.chronicDiseasesAndDrugs = chronicDiseasesAndDrugs;
    }

    public String getVaccination() {
        return vaccination;
    }

    public void setVaccination(String vaccination) {
        this.vaccination = vaccination;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Plec getPlec() {
        return plec;
    }

    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    public String getIlnessHistory() {
        return ilnessHistory;
    }

    public void setIlnessHistory(String ilnessHistory) {
        this.ilnessHistory = ilnessHistory;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }
//
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
    public Patient(String name, String surname, int age, String description) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.description = description;
    }


    public Patient() {

    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                '}';
    }
}
