package com.ericom.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericom.os.domain.Cliente;
import com.ericom.os.domain.OS;
import com.ericom.os.domain.Tecnico;
import com.ericom.os.domain.enuns.Prioridade;
import com.ericom.os.domain.enuns.Status;
import com.ericom.os.repositories.OSRepository;
import com.ericom.os.services.exceptions.ObjectNotFoundException;

import br.senai.os.dtos.OSDTO;

@Service
public class OsService {
	@Autowired
	private OSRepository repository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public OS findById(Integer id) {

		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo:" + OS.class.getName()));

	}

	public List<OS> findAll() {

		return repository.findAll();

	}

	public OS create(@Valid OSDTO obj) {

		return fromDTO(obj);
	}

	public OS update(@Valid OSDTO obj) {

		findById(obj.getId());
		return fromDTO(obj);

	}

	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());

		newObj.setObservacoes(obj.getObservacoes());

		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));

		newObj.setStatus(Status.toEnum(obj.getStatus()));

		Tecnico tec = tecnicoService.findById(obj.getTecnico());

		Cliente cli = clienteService.findById(obj.getCliente());

		newObj.setTecnico(tec);

		newObj.setCliente(cli);

		if (newObj.getStatus().getCod().equals(2)) {
			
			newObj.setDataFechamento(LocalDateTime.now());
			
		}
		
		
		return repository.save(newObj);

	}

}
