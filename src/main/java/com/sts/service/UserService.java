package com.sts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sts.entity.Person;
import com.sts.repository.UserRepository;

@Service
public interface UserService extends UserRepository{

	Person findByName(String userName);

}
