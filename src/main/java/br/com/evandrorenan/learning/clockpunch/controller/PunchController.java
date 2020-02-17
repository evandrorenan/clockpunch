package br.com.evandrorenan.learning.clockpunch.controller;

//HATEOAS
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.evandrorenan.learning.clockpunch.service.PunchDto;
import br.com.evandrorenan.learning.clockpunch.service.PunchService;
import br.com.evandrorenan.learning.clockpunch.utils.ClockPunchUtils;
 
@RestController
public class PunchController {
	
	@Autowired
	private PunchService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/punches/{id}")
	public Resource<PunchDto> getPunch(@PathVariable @Valid @Positive(message="{id_must_be_positive}") Long id) {
		
		PunchDto punchDto = service.getPunchById(id);

		//HATEOAS - Hypermedia as The Engine of Application State
	    Resource<PunchDto> resource = new Resource<>(punchDto);
	    ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getPunches());
	    
	    resource.add(linkTo.withRel(
	    		ClockPunchUtils.getMessage("field.all_punches", messageSource)));
		
		return resource;
	}
	
	@GetMapping(path="/punches")
	public List<PunchDto> getPunches(){
		return service.getAllPunches();
	}
	
	@PostMapping(path="/punches")
	public ResponseEntity<PunchDto> setPunch(@Valid @RequestBody PunchDto punchDto) {
		PunchDto savedPunchDto = service.setPunch(punchDto);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedPunchDto.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}