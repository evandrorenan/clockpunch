package br.com.evandrorenan.learning.clockpunch.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.com.evandrorenan.learning.clockpunch.entity.Punch;
import br.com.evandrorenan.learning.clockpunch.entity.PunchRepository;
import br.com.evandrorenan.learning.clockpunch.exception.PunchNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class PunchService {

	@Autowired
	private PunchRepository repository;
	
	@Autowired
	private MessageSource messageSource;
	
	public PunchDto getPunchById(Long id) {
		
		Punch punch = repository.findById(id).orElseThrow(() ->
			new PunchNotFoundException("id:" + id));
		
		return convertPunchToDto(punch);
	}
	
	public List<PunchDto> getAllPunches(){
		return convertPunchToDto(repository.findAll());
	}	
	
	public PunchDto setPunch(PunchDto punchDto) {
		Punch savedPunch = repository.save(convertDtoToPunch(punchDto));
		return convertPunchToDto(savedPunch);
	}
	
	private List<PunchDto> convertPunchToDto(List<Punch> punches){
		List<PunchDto> punchDtoList = new ArrayList<>();
		for (Punch punch : punches) {
			punchDtoList.add(convertPunchToDto(punch));
		}
		return punchDtoList;
	}
	
	private PunchDto convertPunchToDto(Punch punch) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PropertyMap<Punch, PunchDto>() {
			protected void configure() {
				map().setTimestamp(source.getTimestamp());
			}
		});

		PunchDto punchDto = new PunchDto();
		modelMapper.map(punch, punchDto);
		return punchDto;
	}
		
	private Punch convertDtoToPunch(PunchDto punchDto) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PropertyMap<PunchDto, Punch>() {
			protected void configure() {
				map().setTimestamp(source.getTimestamp());
			}
		});

		Punch punch = new Punch();
		modelMapper.map(punchDto, punch);
		return punch;
	}

}
