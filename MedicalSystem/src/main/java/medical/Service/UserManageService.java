package medical.Service;

import medical.Model.Patient;
import medical.Model.Visit;
import medical.Repository.UserManageRepo;
import medical.exception.RecordNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManageService {

    @Autowired
    UserManageRepo repository;

    public List<Patient> getAllPatients() {
        System.out.println("getAllPatients");
        List<Patient> result = (List<Patient>) repository.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }
    public Patient getPatientById(Long id) throws RecordNotFoundException {
        System.out.println("getPatientById");
        Optional<Patient> patientOptional = repository.findById(id);

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            Hibernate.initialize(patient.getVisits());
            return patient;
        } else {
            throw new RecordNotFoundException("No patient record exists for the given id");
        }
    }

//    public Patient getPatientById(Long id) throws RecordNotFoundException {
//        System.out.println("getPatientById");
//        Optional<Patient> patient = repository.findById(id);
//
//        if (patient.isPresent()) {
//            return patient.get();
//        } else {
//            throw new RecordNotFoundException("No patient record exist for given id");
//        }
//    }

    public Patient createOrUpdatePatient(Patient patient) {
        System.out.println("createOrUpdatePatient");
        // Create new entry
        if (patient.getId() == null) {
            patient = repository.save(patient);
            System.out.println("Patient have null id");
            return patient;
        } else {
            // update existing entry
            Optional<Patient> patientOptional = repository.findById(patient.getId());

            if (patientOptional.isPresent()) {
                Patient existingPatient = patientOptional.get();
                existingPatient.setName(patient.getName());
                existingPatient.setSurname(patient.getSurname());
                existingPatient.setAge(patient.getAge());
                existingPatient.setDescription(patient.getDescription());

                existingPatient = repository.save(existingPatient);

                return existingPatient;
            } else {
                patient = repository.save(patient);
                return patient;
            }
        }
    }

    public Patient addPatient(Patient patient){
        return repository.save(patient);
    }

    public void deletePatientById(Long id) throws RecordNotFoundException {
        System.out.println("deletePatientById");

        Optional<Patient> patient = repository.findById(id);

        if (patient.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No patient record exist for given id");
        }
    }
//    public Patient addVisitToPatient(Long patientId, Visit visit) throws RecordNotFoundException {
//        Optional<Patient> optionalPatient = repository.findById(patientId);
//
//        if (optionalPatient.isPresent()) {
//            Patient patient = optionalPatient.get();
//            visit.setPatient(patient);
//            patient.getVisits().add(visit);
//            repository.save(patient);
//            return patient;
//        } else {
//            throw new RecordNotFoundException("No patient record exist for given id");
//        }
//    }
@Transactional
public Patient addVisitToPatient(Long patientId, Visit visit) throws RecordNotFoundException {
    Optional<Patient> optionalPatient = repository.findById(patientId);

    if (optionalPatient.isPresent()) {
        Patient patient = optionalPatient.get();

        // Inicjalizacja kolekcji visits przed zakończeniem sesji Hibernate
        Hibernate.initialize(patient.getVisits());

        visit.setPatient(patient);
        patient.getVisits().add(visit);
        repository.save(patient);
        return patient;
    } else {
        throw new RecordNotFoundException("No patient record exists for the given id");
    }
}

    public Patient updateVisit(Long patientId, Visit visit) throws RecordNotFoundException {
        Optional<Patient> optionalPatient = repository.findById(patientId);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            List<Visit> visits = patient.getVisits();

            // Znajdź wizytę i zaktualizuj jej dane
            for (Visit existingVisit : visits) {
                if (existingVisit.getId().equals(visit.getId())) {
                    existingVisit.setVisitDate(visit.getVisitDate());
                    // ... inne pola wizyty
                    break;
                }
            }

            repository.save(patient);
            return patient;
        } else {
            throw new RecordNotFoundException("No patient record exist for given id");
        }
    }


}
