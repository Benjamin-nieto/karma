package com.demo.crud.interfacesService;

import java.util.List;
import java.util.Optional;

import com.demo.crud.model.*;

/*Funciones del crud*/
public interface IUserService {
	
	public List<User> listUsers();
	public Optional<User> listId();
	public int save(User us);
	public void delete(int id);

}
