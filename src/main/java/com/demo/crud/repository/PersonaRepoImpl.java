package com.demo.crud.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("Persona1")
public class PersonaRepoImpl implements IPersona{

	private static final Logger log = LoggerFactory.getLogger(PersonaRepoImpl.class);

	
	@Override
	public void registrar(String nombre) {
		// TODO Auto-generated method stub
		log.info("Usuario registrado: "+nombre);
	}

	
}
