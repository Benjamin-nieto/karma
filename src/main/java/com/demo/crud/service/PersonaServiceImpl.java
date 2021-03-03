package com.demo.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.demo.crud.repository.IPersona;
import com.demo.crud.repository.PersonaRepoImpl;

@Service
public class PersonaServiceImpl implements IPersonaService{

	@Autowired
	@Qualifier("Persona2")
	private  IPersona repo;
	
	@Override
	public void registrar(String nombre) {
		// TODO Auto-generated method stub
//		repo = new PersonaRepoImpl(); gracias a la injeccion de dependencias de spring definiendo una notacion
// y colocando autowired en la instancia que quieren auto iniciar por el framework.
		
		
		// los estereotipos solamente registran bin en el contenedor de spring 
		repo.registrar(nombre);
		
	}

}
