package com.demo.crud.interfaces;

import org.springframework.data.repository.CrudRepository;
import com.demo.crud.model.*;


public interface IUser extends CrudRepository<User, Integer>{

}
