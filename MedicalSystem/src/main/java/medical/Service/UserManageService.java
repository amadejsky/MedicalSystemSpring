package medical.Service;

import medical.Model.Patient;
import medical.Repository.UserManageRepo;
import medical.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Patient> patient = repository.findById(id);

        if (patient.isPresent()) {
            return patient.get();
        } else {
            throw new RecordNotFoundException("No patient record exist for given id");
        }
    }

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

    public void deletePatientById(Long id) throws RecordNotFoundException {
        System.out.println("deletePatientById");

        Optional<Patient> patient = repository.findById(id);

        if (patient.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No patient record exist for given id");
        }
    }


}
