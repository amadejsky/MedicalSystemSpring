package medical.Controller;

import medical.Model.Patient;
import jakarta.validation.Valid;
import medical.Service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
@Controller
public class UserManageController {

    private final UserManageService userManageService;



    @Autowired
    public UserManageController(UserManageService userManageService) {
        this.userManageService = userManageService;
    }
    @GetMapping("/signup")
    public String showSignUpForm(Patient patient) {
        return "add-user";
    }

//    @PostMapping("/adduser")
//    public String addUser(@Valid Patient patient, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "add-user";
//        }
//
//        userManageService.save(patient);
//        return "redirect:/index";
//    }

        @PostMapping("/adduser")
    public String addUser(@Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
        userManageService.save(patient);
        model.addAttribute("model",new Patient());
            model.addAttribute("message", "Z powodzeniem dodano pacjenta do bazy danych");
        return "redirect:/index";
    }

//    @PostMapping
//    String addProject(
//            @ModelAttribute("project") @Valid ProjectWriteModel current,
//            BindingResult bindingResult,
//            Model model
//    ) {
//        if (bindingResult.hasErrors()) {
//            return "projects";
//        }
//        service.save(current);
//        model.addAttribute("project", new ProjectWriteModel());
//        model.addAttribute("projects", getProjects());
//        model.addAttribute("message", "Z powodzeniem dodano pacjenta do bazy danych");
//        return "projects";
//    }
    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userManageService.findAll());
        return "index";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Patient patient = userManageService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));

        model.addAttribute("user", patient);
        return "update-patient";
    }
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Patient patient,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            patient.setId(id);
            return "update-patient";
        }

        userManageService.save(patient);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Patient patient = userManageService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        userManageService.delete(patient);
        return "redirect:/index";
    }

}
