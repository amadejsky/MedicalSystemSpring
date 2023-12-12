package medical.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity

public class Patient {
    public Patient(String name, String surname, Integer age, String description) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Patient() {

    }

    public long getId() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank(message = "Imię pacjenta jest wymagane")
    private String name;
    @NotBlank(message = "Nazwisko pacjenta jest wymagane")
    private String surname;
    @NotNull(message = "Wiek pacjenta jest wymagany")
    private Integer age;
    @NotBlank(message = "Opis zdrowotny pacjenta jest wymagany")
    private String description;


}
