package br.com.evandrorenan.learning.clockpunch.service;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor	//JPA expects a Simple Constructor in every class
@ToString 
public class PunchDto {
	
	private long id;

	@Pattern(regexp = "^[A-Za-z0-9]*$", message="{personId_must_have_only_letters_and_numbers}")
	@Size (max=18, message="{personId_cannot_exceeds_18_characters}")
	private String personId;
	
	@PastOrPresent(message="{timestamp_cannot_future}")
	@NotNull(message="{timestamp_is_obligatory}")
	private Date timestamp;
	
	private String location;
	
}
