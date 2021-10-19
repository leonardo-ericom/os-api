package com.ericom.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ericom.os.domain.Tecnico;
import com.ericom.os.services.TecnicoService;

import br.senai.os.dtos.TecnicoDTO;
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findbyId(@PathVariable Integer id) {

		Tecnico obj = service.findById(id);
		TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);

	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {

		List<TecnicoDTO> listDTO = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());

		/**
		 * o metodo acima pode ser feito assim tb List<Tecnico>list=service.findAll();
		 * List<TecnicoDTO> listDTO = new ArrayList<>();
		 * 
		 * for (Tecnico obj : list) { listDTO.add(new TecnicoDTO(obj));
		 * 
		 * }
		 * 
		 * list.forEach(obj -> listDTO.add(new TecnicoDTO(obj)));
		 */

		return ResponseEntity.ok().body(listDTO);

	}

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	/*
	 * 
	 * Atualiza um tecnico
	 * 
	 */

	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {

		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);

	}

	/*
	 * 
	 * Delete Tecnico
	 */

	@DeleteMapping(value= "/{id}")
	public ResponseEntity<Void>delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
		
		
		
	}
}
