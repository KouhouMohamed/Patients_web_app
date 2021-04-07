package com.kouhou.springMVC.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="PATIENTS")
@Data
@NoArgsConstructor 
@AllArgsConstructor	
public class Patient{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	
	@NotEmpty
	@Size(max = 10, min=3)
	@Column(name="NAME", length=30)
	private String name;
	@Temporal(TemporalType.DATE)
	@Column(name="BIRTH_DATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateBirth;
	@DecimalMin(value = "100")
	@Column(name="SCORE")
	private int score;
	@Column(name="IS_SICK")
	private boolean sick;
}
