package medical.Controller;

import medical.Model.Patient;
import medical.Model.Visit;
import medical.Service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import medical.exception.RecordNotFoundException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String deletePatientById(Model model, @PathVariable("id") Long id)
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

    @RequestMapping(path = "/homepage")
    public String homepage(Model model){
        return "homepage";
    }

    @RequestMapping(path="/fdaapi")
    public String fdaApi(Model fda){

        return "fdaapi";
    }
    @GetMapping("/addVisit/{id}")
    public String showAddVisitForm(@PathVariable Long id, Model model) throws RecordNotFoundException {
        Patient patient = service.getPatientById(id);
        model.addAttribute("patient", patient);

        Visit visit = new Visit();
        visit.setPatient(patient);
        model.addAttribute("visit",visit);

        return "add-visit";
    }

    @PostMapping(path = "/visit/{patientId}")
    public String addVisit(
            @PathVariable("patientId") Long patientId,
            @RequestParam(name = "visitDate", required = false) LocalDateTime visitDate
    ) throws RecordNotFoundException {
        Visit visit = new Visit();
        visit.setVisitDate(visitDate);

        service.addVisitToPatient(patientId, visit);


        return "redirect:/list-patients";

    }

    @PostMapping(path = "/updateVisit/{patientId}")
    public String updateVisit(@PathVariable("patientId") Long patientId, @RequestParam("visitId") Long visitId, @RequestParam(name = "visitDate", required = false) LocalDateTime visitDate) throws RecordNotFoundException {
        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setVisitDate(visitDate);

        service.updateVisit(patientId, visit);

        return "add-visit";
    }

    @GetMapping("/editVisitForm/{id}")
    public String showEditiVisitForm(@PathVariable Long id, Model model) throws RecordNotFoundException {
        Visit visit = service.getVisitById(id);
        model.addAttribute("visit", visit);
        Patient patient = visit.getPatient();
        model.addAttribute("patient", patient);
        service.updateVisit(patient.getId(),visit);
        return "edit-visit";
    }
    @PostMapping("/editvisit/{id}")
    public String editVisit(@PathVariable Long id, Visit editedVisit) throws RecordNotFoundException {
        editedVisit.setId(id);
        service.updateVisitFromId(editedVisit);
        return "redirect:/list-patients";
    }
    @PostMapping("/deletevisit/{id}")
    public String deleteVisit(@PathVariable Long id, Visit editedVisit) throws RecordNotFoundException {
      //  editedVisit.setId(id);
       // Patient patient = editedVisit.getPatient();
        service.deleteVisitById(id);
       // System.out.println("delete visit by id after service delete"+editedVisit.getId());
       // System.out.println("delete visit by id after service delete"+id);
        return "redirect:/list-patients";
    }
    @GetMapping("/additionalMedicalInfoForm/{id}")
    public String showAdditionalInfoForm(@PathVariable Long id, Model model) throws RecordNotFoundException{
        Patient patient = service.getPatientById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("patientId", id);

        return "additionalMedicalInfo";
    }

    @PostMapping("/submitMedicalInfo/{patientId}")
    public String submitMedicalInfo(@PathVariable ("patientId") Long id,@ModelAttribute("patient") Patient patient,
    @RequestParam("ilnessHistoryDate") String ilnessHistoryDate,
    RedirectAttributes redirectAttributes) throws RecordNotFoundException {
//        System.out.println("Received patient data:");
//        System.out.println("Weight: " + patient.getWeight());
//        System.out.println("Plec: " + patient.getPlec());
//        System.out.println("Ilness History: " + patient.getIlnessHistory());
//        System.out.println("Contraindications: " + patient.getContraindications());
//        patient.setWeight(patient.getWeight());
        String ilnessHistoryWithDate = patient.getIlnessHistory() + " - Approximated Date: " + ilnessHistoryDate;
        patient.setIlnessHistory(ilnessHistoryWithDate);
        service.updatePatientMedicalInfo(patient, id);
        return "redirect:/list-patients";
    }

    @GetMapping("/showInfo/{id}")
    public String showInfo(@PathVariable Long id, Model model) throws RecordNotFoundException{
        Patient patient = service.getPatientById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("patientId", id);

        List<Patient> list = service.getAllPatients();

        model.addAttribute("patients", list);

        return "patientInfo";
    }



}

