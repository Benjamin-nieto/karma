package com.demo.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.crud.interfaces.IUser;
import com.demo.crud.interfacesService.IUserService;
import com.demo.crud.model.*;

public class UserService implements IUserService{

	@Autowired
	private IUser data;

	@Override
	public List<User> listUsers() {
		// TODO Auto-generated method stub
		return (List<User>)data.findAll();
	}

	@Override
	public Optional<User> listId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(User us) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	
	

}
