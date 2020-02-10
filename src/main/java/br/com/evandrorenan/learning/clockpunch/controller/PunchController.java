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

import br.com.evandrorenan.learning.clockpunch.ClockPunchUtils;
import br.com.evandrorenan.learning.clockpunch.entity.Punch;
import br.com.evandrorenan.learning.clockpunch.exception.PunchNotFoundException;
import br.com.evandrorenan.learning.clockpunch.service.PunchDaoService;
 
@RestController
public class PunchController {
	
	@Autowired
	private PunchDaoService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/punches/{id}")
	public Resource<Punch> getPunch(@PathVariable @Valid @Positive(message="{id_must_be_positive}") Long id) {
		Punch punch = service.getPunchById(id);

		if (punch==null) {
			throw new PunchNotFoundException("id:" + id);
		}

		//HATEOAS - Hypermedia as The Engine of Application State
	    Resource<Punch> resource = new Resource<>(punch);
	    ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getPunches());
	    
	    resource.add(linkTo.withRel(
	    		ClockPunchUtils.getMessage("field.all_punches", messageSource)));
		
		return resource;
	}
	
	@GetMapping(path="/punches")
	public List<Punch> getPunches(){

		return service.getAllPunches();
	}
	
	@PostMapping(path="/punches")
	public ResponseEntity<Punch> setPunch(@Valid @RequestBody Punch punch) {
		Punch savedPunch = service.savePunch(punch);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedPunch.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}