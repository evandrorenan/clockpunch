package br.com.evandrorenan.learning.clockpunch.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

	public List<Punch> getAllPunches(){
		List<Punch> punches = punchRepository.findAll();
		return punches;
	}
	
	public Punch getPunchById(long id) {
		Optional<Punch> punch = punchRepository.findById(id);
		return punch.orElse(null);
	}
	
	public Punch savePunch(Punch punch) {
		punchRepository.save(punch);
		return punch;
	}	
}