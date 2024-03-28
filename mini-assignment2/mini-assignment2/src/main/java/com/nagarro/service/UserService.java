package com.nagarro.service;


import java.util.*;
import java.util.stream.Collectors;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.entity.User;
import com.nagarro.repo.UserRepo;
import com.nagarro.executor.ApiCall;
import com.nagarro.executor.RequestExecutor;
import com.nagarro.executor.Task;
import com.nagarro.model.Gender;
import com.nagarro.model.Nationality;
import com.nagarro.model.UserApi;
import com.nagarro.model.UserData;
import com.nagarro.model.UserData.NameData;
import com.nagarro.model.UsersResponse;
import com.nagarro.model.PageInfo;
import com.nagarro.exception.*;

@Service
public class UserService {
	
	private final UserRepo userRepo;
	private final WebClient userWebClient;
	private final WebClient nationalityWebClient;
	private final WebClient genderWebClient;
	
	public UserService(UserRepo userRepo, WebClient userWebClient, WebClient nationalityWebClient,
			WebClient genderWebClient) {
		
		this.userRepo = userRepo;
		this.userWebClient = userWebClient;
		this.nationalityWebClient = nationalityWebClient;
		this.genderWebClient = genderWebClient;
	}
	
	public List<User> getUsersFromApi(int size){
		RequestExecutor requestExecutor=new RequestExecutor();
		
		Task<UserApi> getUserTask=ApiCall.create(userWebClient,UserApi.class)
				.queryParam("results",size)
				.build();
		
		UserApi userApi=requestExecutor.execute(getUserTask);
		
		List<User> users = new ArrayList<>();
		
		for(UserData userData:userApi.getResults()) {
			User user = new User(null, null, null, null, null, null, null, null, null);
			
			NameData nameData = userData.getName();
			user.setName(userData.getName().getFirst()+" "+userData.getName().getLast());
			user.setAge(userData.getDob().getAge());
			user.setGender(userData.getGender());
			user.setNationality(userData.getNat());
			user.setDob(userData.getDob().getDate());
			
			long created=System.currentTimeMillis();
			long modified=System.currentTimeMillis();
			user.setDateCreated(created);
			user.setDateModified(modified);
			
			Task<Nationality> getNationality=ApiCall
					.create(nationalityWebClient,Nationality.class)
					.queryParam("name",nameData.getFirst())
					.build();
			
			Task<Gender> getGender=ApiCall
					.create(genderWebClient,Gender.class)
					.queryParam("name",nameData.getFirst())
					.build();
			
			List<Object> result = requestExecutor.executeConcurrently(getNationality,getGender);
			
			Nationality nationality=(Nationality)result.get(0);
			Gender gender=(Gender)result.get(1);
			
			boolean nationalityVerified =nationality.getCountry().stream()
					.anyMatch(country -> country.getCountry_id().equals(user.getNationality()));
			
			boolean genderVerified = gender.getGender().equalsIgnoreCase(user.getGender());
			
			if(nationalityVerified && genderVerified) {
				user.setVerificationStatus("VERIFIED");
			}
			else {
				user.setVerificationStatus("TO_BE_VERIFIED");
			}
			users.add(user);
			
			
		}
		return users;
	}
	
	public List<User> addUsers(List<User> users){
		return userRepo.saveAll(users);
	}
	
	
	 public List<User> getUsersFromDatabase() {
	        return userRepo.findAll();
	    }
	    
	public UsersResponse getUsers(String sortType, String sortOrder, int limit, int offset) {
        // Assuming users is a list of User objects
        List<User> users = userRepo.findAll();

        Comparator<User> comparator = createComparator(sortType, sortOrder);

        List<User> sortedUsers = users.stream()
                .sorted(comparator)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());

        long total = users.size();

        boolean hasNextPage = hasNextPage(limit, offset);

        boolean hasPreviousPage = hasPreviousPage(offset);
        
        if(limit<1 || limit>5) {
			System.err.println("Validation error! Limit should be between 1 and 5 ");
			return  UsersResponse(null);
		 	
		 }
        
        PageInfo pageInfo = new PageInfo(offset,limit, hasNextPage, hasPreviousPage,total);

        return new UsersResponse(sortedUsers,  pageInfo);
    }
   
    private UsersResponse UsersResponse(List<User> sortedUsers) {
		// TODO Auto-generated method stub
		return null;
	}

	private Comparator<User> createComparator(String sortType, String sortOrder) {
        Comparator<User> comparator;

        if ("Age".equalsIgnoreCase(sortType)) {
            comparator = Comparator.comparing(User::getAge);
        } else if ("Name".equalsIgnoreCase(sortType)) {
            comparator = Comparator.comparing(User::getName, Comparator.comparingInt(String::length));
        } else {
            throw new IllegalArgumentException("Invalid sortType: " + sortType);
        }

        if ("Even".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.thenComparing((u1, u2) -> Integer.compare(u1.getAge() % 2, u2.getAge() % 2));
        } else if ("Odd".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.thenComparing((u1, u2) -> Integer.compare(u1.getName().length() % 2, u2.getName().length() % 2));
        } else {
            throw new IllegalArgumentException("Invalid sortOrder: " + sortOrder);
        }

        return comparator;
    }

    private boolean hasNextPage(int limit, int offset) {
   	 List<User> users = userRepo.findAll();
   	 long total = users.size(); 

       // Calculate the expected next offset
       int nextOffset = offset + limit;

       // Check if there are more items beyond the expected next offset
       return nextOffset < total;
   }
   private boolean hasPreviousPage(int offset) {
       // Check if there is a previous page based on the current offset
       return offset > 0;
   }

    


	
}
