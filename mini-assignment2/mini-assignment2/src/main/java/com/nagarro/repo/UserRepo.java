package com.nagarro.repo;



import org.springframework.data.jpa.repository.JpaRepository;



import com.nagarro.entity.User;

public interface UserRepo extends JpaRepository<User,Long> {


	
	 

}
