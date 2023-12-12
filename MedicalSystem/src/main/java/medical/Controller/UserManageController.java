package medical.Controller;

import medical.Model.Patient;
import jakarta.validation.Valid;
import medical.Repository.UserManageRepo;
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

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.h2.db.exception.RecordNotFoundException;
import com.h2.db.model.EmployeeEntity;
import com.h2.db.service.EmployeeService;

@Controller
@RequestMapping("/")
public class UserManageController
{
    @Autowired
    UserManageService service;

    @RequestMapping
    public String getAllEmployees(Model model)
    {
        System.out.println("getAllEmployees");

        List<Patient> list = service.getAllPatients();

        model.addAttribute("employees", list);

        return "list-employees";
    }




    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editPatientById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {

        System.out.println("editEmployeeById" + id);
        if (id.isPresent()) {
            Patient patient = service.getPatientById(id.get());
            model.addAttribute("employee", patient);
        } else {
            model.addAttribute("employee", new Patient());
        }


        return "add-edit-employee";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {

        System.out.println("deleteEmployeeById" + id);

        service.deletePatientById(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/createPatient", method = RequestMethod.POST)
    public String createOrUpdatePatient(Patient patient)
    {
        System.out.println("createOrUpdateEmployee ");

        service.createOrUpdatePatient(patient);

        return "redirect:/";
    }
}

//    @Autowired
//    private final UserManageService userManageService;
//
//
//
//    @Autowired
//    public UserManageController(UserManageService userManageService) {
//        this.userManageService = userManageService;
//    }
//    @GetMapping("/signup")
//    public String showSignUpForm(Patient patient) {
//        return "add-user";
//    }
//
//        @PostMapping("/adduser")
//    public String addUser(@Valid Patient patient, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "add-user";
//        }
//        userManageService.save(patient);
//        model.addAttribute("model",new Patient());
//            model.addAttribute("message", "Z powodzeniem dodano pacjenta do bazy danych");
//        return "redirect:/index";
//    }
//
//
//    @GetMapping("/index")
//    public String showUserList(Model model) {
//        model.addAttribute("users", userManageService.findAll());
//        return "index";
//    }
//    @GetMapping("/edit/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        Patient patient = userManageService.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
//
//        model.addAttribute("user", patient);
//        return "update-patient";
//    }
//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid Patient patient,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            patient.setId(id);
//            return "update-patient";
//        }
//
//        userManageService.save(patient);
//        return "redirect:/index";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") long id, Model model) {
//        Patient patient = userManageService.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
//        userManageService.delete(patient);
//        return "redirect:/index";
//    }

}
