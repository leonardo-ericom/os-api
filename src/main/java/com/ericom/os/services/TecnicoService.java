package com.ericom.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericom.os.domain.Pessoa;
import com.ericom.os.domain.Tecnico;
import com.ericom.os.repositories.PessoaRepository;
import com.ericom.os.repositories.TecnicoRepository;
import com.ericom.os.services.exceptions.DataIntegratyViolationException;
import com.ericom.os.services.exceptions.ObjectNotFoundException;

import br.senai.os.dtos.TecnicoDTO;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	

	/*
	 * Busca tecnico pelo Id
	 * 
	 */
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));

	}

	public List<Tecnico> findAll() {
		return repository.findAll();

	}

	public Tecnico create(TecnicoDTO objDTO) {

		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");

		}
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {

		Tecnico oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");

		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);

	}

	public void delete(Integer id) {

		Tecnico obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui  ordens de serviço! Nâo pode ser deletado!");

		}

		repository.deleteById(id);

	}

	private Pessoa findByCPF(TecnicoDTO objDTO) {

		Pessoa obj = pessoaRepository.finByCPF(objDTO.getCpf());

		if (obj != null) {

			return obj;

		}
		return null;

	}

}
