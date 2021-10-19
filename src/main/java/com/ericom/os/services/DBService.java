package com.ericom.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ericom.os.domain.Cliente;
import com.ericom.os.domain.OS;
import com.ericom.os.domain.Tecnico;
import com.ericom.os.domain.enuns.Prioridade;
import com.ericom.os.domain.enuns.Status;
import com.ericom.os.repositories.ClienteRepository;
import com.ericom.os.repositories.OSRepository;
import com.ericom.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Valdir Cezar", "006.612.371-25", "(88) 98888-8888");
		Tecnico t2 = new Tecnico(null, "Lonel Messi", "641.760.040-88", "(88) 97888-8888");
		Cliente c1 = new Cliente(null, "Betina Campos", "873.766.601-00", "(64) 97777-7777");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OD", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
