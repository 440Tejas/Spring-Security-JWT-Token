package com.sts.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.DTO.PersonDTO;
import com.sts.DTO.PersonDTO2;
import com.sts.config.JwtTokenUtil;
import com.sts.entity.Person;
import com.sts.service.UserService;

@RestController
@RequestMapping("api/v1")
public class TestController {
	@Autowired
	  private UserService userRepository;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenHelper;
	@Autowired
	private ModelMapper modelMapper;
//	
//
//	
	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String,String> req) {
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			Person user=new Person();
			user.setName(req.get("name"));
			user.setPassword(req.get("password"));
			
			System.out.println(user.getName()+ "  " +user.getPassword());
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
			UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(user.getName());
			String token = jwtTokenHelper.generateToken(loadUserByUsername);
			if(token!=null) {
				map.put("success", true);
				map.put("token", token);
			}

		}
		catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("MESSAGE", "ERROR_IN_FETCHING_DATA");
		}
		return ResponseEntity.ok(map);
		
	}

	@PostMapping("/tokenParse")
	public ResponseEntity<Map<String,Object>> tokenParse(@RequestBody String token){
		Map<String,Object> map=new LinkedHashMap<String,Object>();
		
		try {
		String decodedToken=jwtTokenHelper.testDecodeJWT(token);
		JSONObject json = new JSONObject(decodedToken);
		map.put("fullName", json.getString("fullName"));
		map.put("StaffId", json.get("StaffId"));
		map.put("email", json.getString("emailsa"));
	   }
		catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/display")
	public Person display() {
		try {
			return userRepository.findByName("raj");	
		}
		catch (Exception e) {
			System.out.println("Something went wrong..");
		}
		return null;
		
		
	}

	@GetMapping("/display2")
	public Person display2() {
		try {
			return userRepository.findById(2).orElse(null);	
		}
		catch (Exception e) {
			System.out.println("Something went wrong..");
		}
		return null;
		
		
	}
	
	@GetMapping("/findBycid/{id}")
	public ResponseEntity<Map<String,Object>> findBycid(@PathVariable int id)
	{
		Map<String,Object> map=new HashMap<>();
		Person person = userRepository.findById(id).orElse(null);
		PersonDTO personDTO= this.modelMapper.map(person, PersonDTO.class);
	//	PersonDTO findcid = userRepository.findcid(id);
		//map.put("DATA1", personDTO);
		map.put("DATA2", personDTO);
		return ResponseEntity.ok(map);
		
	}
	
	@GetMapping("/searchByAdressOrName/{pattern}")
	public ResponseEntity<Map<String,Object>> searchByAdressOrName(@PathVariable String pattern){
		Map<String,Object> map=new HashMap<>();
		         try {
		        	 List<Object[]> sarchByPattern = userRepository.sarchByPattern(pattern);
			         List<PersonDTO2> personDTO2Data=new ArrayList<>();
			         for(Object[]  object:sarchByPattern)
			         {
			        	 PersonDTO2 personDTO2=new PersonDTO2();
			        	 personDTO2.setName(object[0]!=null ? object[0].toString():"");
			        	 personDTO2.setAddress(object[1]!=null?object[1].toString():"");
			        	 personDTO2Data.add(personDTO2);
			         }
			         map.put("DATA", personDTO2Data);
			         map.put("SUCCESS",true);
		         }
		         catch(Exception e) {
		        	 e.printStackTrace();
		         }
		         
		return ResponseEntity.ok(map);
		
	}
}
