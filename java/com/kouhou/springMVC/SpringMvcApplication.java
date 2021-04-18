package com.kouhou.springMVC;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kouhou.springMVC.entities.Patient;
import com.kouhou.springMVC.entities.User;
import com.kouhou.springMVC.repositories.PatientRepository;
import com.kouhou.springMVC.repositories.UserRepository;

@SpringBootApplication
public class SpringMvcApplication implements CommandLineRunner {

	@Autowired
	private PatientRepository patientRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		patientRepository.save(new Patient(null, "Kouhou", new Date(),100,false));
		patientRepository.save(new Patient(null, "Mohamed", new Date(),200,true));
		patientRepository.save(new Patient(null, "Enset", new Date(),300,false));
		patientRepository.save(new Patient(null, "Settat", new Date(),400,true));
		patientRepository.save(new Patient(null, "MedKoh", new Date(),100,false));
		patientRepository.save(new Patient(null, "Mohamed", new Date(),200,true));
		patientRepository.save(new Patient(null, "FSTS", new Date(),300,false));
		patientRepository.save(new Patient(null, "Mohammedia", new Date(),400,true));
		patientRepository.save(new Patient(null, "Tata", new Date(),100,false));
		patientRepository.save(new Patient(null, "AKKA", new Date(),200,true));
		patientRepository.save(new Patient(null, "Ibrahim", new Date(),300,false));
		patientRepository.save(new Patient(null, "Aicha", new Date(),400,true));
		
		
	}

}
