package com.sts.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sts.entity.Person;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	
  @Autowired
  UserService userRepository;

  
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Person person = null;
		try {
			person = userRepository.findByName(userName);
			if (person != null) {
				return new User(person.getName(), person.getPassword(), new ArrayList<>());
			}
		} catch (Exception e) {
			
		}
		throw new UsernameNotFoundException("Person not found with username: " + userName);
	}
}	

