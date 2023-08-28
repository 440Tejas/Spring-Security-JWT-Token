package com.sts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sts.DTO.PersonDTO;
import com.sts.entity.Person;

@Repository
public interface UserRepository extends JpaRepository<Person, Integer>{
	
	@Query(
	        nativeQuery = true,
	        value = "SELECT new com.sts.DTO.PersonDTO(u.name, u.password, u.address) FROM Person u WHERE u.id = :id")
	PersonDTO findcid(int id);
	
	
	@Query(nativeQuery=true,value="SELECT name, address\r\n"
			+ "FROM jwtdemo.user\r\n"
			+ "WHERE name LIKE %?1% || address LIKE %?1% ")
	List<Object[]> sarchByPattern(String pattern);


}
