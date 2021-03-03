package com.demo.crud.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Persona2")
public class PersonaRepoImpl2 implements IPersona{

	

	private static final Logger log = LoggerFactory.getLogger(PersonaRepoImpl.class);

	
	public void registrar(String nombre) {
		// TODO Auto-generated method stub
		log.info("Usuario registrado en la implementacion 2: "+nombre);
	}
}
