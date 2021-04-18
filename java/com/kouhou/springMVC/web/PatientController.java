package com.kouhou.springMVC.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kouhou.springMVC.entities.Patient;
import com.kouhou.springMVC.repositories.PatientRepository;

@Controller
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	@GetMapping(path="/")
	public String indexPage() {
		return "index";
	}
	
	//function to view list of patients by page
	@GetMapping(path = "/listPatients")
	public String listPatients(Model model, @RequestParam(name="motCle", defaultValue = "") String motCle, @RequestParam(name="page", defaultValue = "0")int page, @RequestParam(name="size", defaultValue = "5")int sizePage, @RequestParam(name="sick", defaultValue = "") String sick) {
		Page<Patient> pagePatients=null;
		//We can seach by sick
		//if not the sick variable equal to  ""
		if(sick.equals("")) {
			//pagePatients = patientRepository.findByNameContainsAndIsSick(motCle,false, PageRequest.of(page, sizePage));
			pagePatients = patientRepository.findByNameContains(motCle, PageRequest.of(page, sizePage));

		}
		else {
			//if we choose to search sick or non sick patients
			pagePatients = patientRepository.findByNameContainsAndSick(motCle, Boolean.valueOf(sick),PageRequest.of(page, sizePage));
		}
		List<Patient> patients = pagePatients.getContent();
		//We add all attributes that we will need to the model
		model.addAttribute("listPatients",patients); 
		model.addAttribute("motCle",motCle);
		model.addAttribute("sick",sick);
		model.addAttribute("pages", new int [pagePatients.getTotalPages()]);
		model.addAttribute("currentPage",page);
		//by the end we return the page witch view patients
		return "patientsView";
	}
	
	//function to add a new patient return the form the enter his informations
	@GetMapping(path = "/addPatient")
	public String addPatient(Model model) {
		model.addAttribute("patient",new Patient());
		return "addPatient";
	}
	//function that add patient to our DB, but before validation and if there is errors we sent them to add patient form
	@PostMapping(path="/savePatient")
	public String savePatient(@Valid Patient patient, BindingResult bindingResult) {
		if(bindingResult.hasErrors()){
			return "addPatient";
			}
		System.out.println(bindingResult.getAllErrors().toString());
		patientRepository.save(patient);
		return "redirect:/listPatients";
	}
	@GetMapping(path="/deletePatient")
	public String deletePatient(Long id) {
		patientRepository.deleteById(id);
		return "redirect:/listPatients";
	}
	@PostMapping("/editPatient")
	public String editPatient(String name, String dateBirth, int score, boolean sick, Long id) throws ParseException {
		
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateBirth);
		patientRepository.update(name, date, score, sick, id);
		return "redirect:/listPatients";
	}
	@GetMapping(path="/getInfoPatient")
	public String getInfoPatient(Model model, Long id) {
		Patient patient = patientRepository.getOne(id);
		model.addAttribute("patientToEdit",patient);
		return "editPatient";
	}
	}

