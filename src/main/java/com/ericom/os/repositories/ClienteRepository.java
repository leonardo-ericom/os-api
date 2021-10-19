package com.ericom.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ericom.os.domain.Cliente;
import com.ericom.os.domain.Pessoa;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	

}
