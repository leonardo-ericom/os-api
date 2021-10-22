package com.ericom.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericom.os.domain.Cliente;
import com.ericom.os.domain.Pessoa;
import com.ericom.os.dtos.ClienteDTO;
import com.ericom.os.repositories.ClienteRepository;
import com.ericom.os.repositories.PessoaRepository;
import com.ericom.os.services.exceptions.DataIntegratyViolationException;
import com.ericom.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	

	/*
	 * Busca tecnico pelo Id
	 * 
	 */
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));

	}

	public List<Cliente> findAll() {
		return repository.findAll();

	}

	public Cliente create(ClienteDTO objDTO) {

		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");

		}
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {

		Cliente oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");

		}
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);

	}

	public void delete(Integer id) {

		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui ordens de serviço! Nâo pode ser deletado!");

		}

		repository.deleteById(id);

	}
	private Pessoa findByCPF(ClienteDTO objDTO) {

		Pessoa obj = pessoaRepository.finByCPF(objDTO.getCpf());

		if (obj != null) {

			return obj;

		}
		return null;

	}

}
