package com.bus.service;

import java.util.List;

import com.bus.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bus.beans.Customer;

@Component
public class CustomerDao {
	
	@Autowired
	private CustomerRepo repo;
	
	
	public int save(Customer customer) {
		
		repo.save(customer);
		return 1;
		
	}
	
//	@Cacheable(cacheNames = "login", key = "'customer'+#email+#password")
	public Customer login(String email, String password) {
		Customer customer = repo.findByEmailAndPassword(email, password);
		return customer;
	}

	public List<Customer> getAll(){
		List<Customer> findAll = repo.findAll();
		return findAll;
	}

	
	public int updateDetail(Customer customer) {
		repo.save(customer);
		return 1;
	}
	
	
	
	

}
