package medical.Service;

import medical.Model.Patient;
import medical.Repository.UserManageRepo;
import medical.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
@Service
public class UserManageService {

    @Autowired
    UserManageRepo repository;

    public List<Patient> getAllPatients()
    {
        System.out.println("getAllPatients");
        List<Patient> result = (List<Patient>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Patient>();
        }
    }


    public Patient getPatientById(Long id)
    {
        System.out.println("getEmployeeById");
        Optional<Patient> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new IllegalArgumentException("No employee record exist for given id");
        }
    }

    public Patient createOrUpdatePatient(Patient patient)
    {
        System.out.println("createOrUpdatePatient");
        // Create new entry
        if(patient.getId()  == null)
        {
            patient = repository.save(patient);

            return patient;
        }
        else
        {
            // update existing entry
            Optional<Patient> employee = repository.findById(patient.getId());

            if(employee.isPresent())
            {
                Patient newpatient = employee.get();
                newpatient.setName(patient.getName());
                newpatient.setSurname(patient.getSurname());
                newpatient.setAge(patient.getAge());
                newpatient.setDescription(patient.getDescription());

                newpatient = repository.save(newpatient);

                return newpatient;
            } else {
                patient = repository.save(patient);

                return patient;
            }
        }
    }

    public void deletePatientById(Long id) throws RecordNotFoundException
    {
        System.out.println("deleteEmployeeById");

        Optional<Patient> employee = repository.findById(id);

        if(employee.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
