package medical.Controller;

import com.couchbase.client.core.error.ServerOutOfMemoryException;
import medical.Model.Patient;
import jakarta.validation.Valid;
import medical.Service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import medical.Service.UserManageService;
import java.util.List;
import java.util.Optional;
import medical.exception.RecordNotFoundException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class UserManageController
{
    @Autowired
    UserManageService service;

    @RequestMapping
    public String getAllPatients(Model model)
    {
        System.out.println("getAllPatients");

        List<Patient> list = service.getAllPatients();

        model.addAttribute("patients", list);

        return "list-patients";
    }




    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editPatientById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {

        System.out.println("editPatientById" + id);
        if (id.isPresent()) {
            Patient patient = service.getPatientById(id.get());
            model.addAttribute("patient", patient);
        } else {
            model.addAttribute("patient", new Patient());
        }


        return "add-edit-patient";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {

        System.out.println("deletePatientById" + id);

        service.deletePatientById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createPatient", method = RequestMethod.POST)
    public String createOrUpdatePatient(Patient patient) {
        System.out.println("Create or update patient");
        service.createOrUpdatePatient(patient);
        return "redirect:/";
    }

    @RequestMapping(path="/addPatientToDataBase", method=RequestMethod.POST)
    public String addPatientToDataBase(Patient patient){
        service.addPatient(patient);
        return "redirect:/";
    }

    @RequestMapping(path = "/addPatient")
    public String addPatient(Model model) throws RecordNotFoundException{
            model.addAttribute("patient", new Patient());
        return "add-user";
    }

    @RequestMapping(path = "/list-patients")
    public String list(Model model){
        List<Patient> list = service.getAllPatients();

        model.addAttribute("patients", list);
        return "list-patients";
    }

    @RequestMapping(path="/fdaapi")
    public String fdaApi(Model fda){

        return "fdaapi";
    }



}

