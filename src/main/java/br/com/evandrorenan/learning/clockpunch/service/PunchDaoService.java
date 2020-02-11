package br.com.evandrorenan.learning.clockpunch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import br.com.evandrorenan.learning.clockpunch.entity.Punch;
import br.com.evandrorenan.learning.clockpunch.entity.PunchRepository;

@Component
@Repository
@Transactional
public class PunchDaoService {
	
	@Autowired
	private PunchRepository punchRepository;
	
	@PersistenceContext
	private EntityManager entityManager; 

	public List<PunchDto> getAllPunches(){
		return convertPunchToDto(punchRepository.findAll());
	}
	
	public PunchDto getPunchById(long id) {
		Optional<Punch> punch = punchRepository.findById(id);
		return convertPunchToDto(punch.orElse(null));
	}
	
	public PunchDto savePunch(PunchDto punchDto) {
		Punch punch = convertDtoToPunch(punchDto);
		punchRepository.save(punch);
		return convertPunchToDto(punch);
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