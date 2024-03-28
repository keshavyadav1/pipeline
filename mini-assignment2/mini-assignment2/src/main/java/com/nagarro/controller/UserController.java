package com.nagarro.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.entity.User;
import com.nagarro.service.UserService;
import com.nagarro.model.UsersResponse;
import com.nagarro.validator.CustomValidator;
import com.nagarro.validator.FactoryValidator;

@RestController
public class UserController {
	
	private UserService service;
	private final FactoryValidator factoryValidator;

    @Autowired
    public UserController(FactoryValidator factoryValidator, UserService service) {
        this.factoryValidator = factoryValidator;
        this.service = service;
    }


	
	@PostMapping("users")
	public List<User> addUser(@RequestParam(name="size",defaultValue="1")Integer size){
		List<User> users=service.getUsersFromApi(size);
		return service.addUsers(users);
	}
	
	@GetMapping("users")
	public List<User> getAllUsers(){
		return service.getUsersFromDatabase();
	}

	@GetMapping("listUser")
    public UsersResponse getUsers(
//            @RequestParam(defaultValue = "Name") String sortType,
//            @RequestParam(defaultValue = "Odd") String sortOrder,
//            @RequestParam(defaultValue = "5") String limit,
//            @RequestParam(defaultValue = "0") String offset) {
    		
    		
    		 @RequestParam String sortType,
             @RequestParam String sortOrder,
             @RequestParam String limit,
             @RequestParam String offset) {

        validateInput("Numeric", limit);
        validateInput("Numeric", offset);
        validateInput("Alphabets", sortType);
        validateInput("Alphabets", sortOrder);
        
      
        return service.getUsers(sortType, sortOrder, Integer.parseInt(limit), Integer.parseInt(offset));
    }

    public void validateInput(String parameterType, String value) {
        CustomValidator validator = factoryValidator.getValidator(parameterType);
        if (!validator.validate(value)) {
            throw new IllegalArgumentException("Invalid input for " + parameterType + ": " + value);
        }
    }
}
