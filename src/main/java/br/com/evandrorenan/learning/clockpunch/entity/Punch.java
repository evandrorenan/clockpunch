package br.com.evandrorenan.learning.clockpunch.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor	//JPA expects a Simple Constructor in every class
@ToString 
@Entity
public class Punch {
	
	@Id
	@GeneratedValue
	private long id;

	private String personId;
	
	private Date timestamp;
	
	private String location;
	
}
