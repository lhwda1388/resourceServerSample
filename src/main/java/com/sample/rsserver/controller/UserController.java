package com.sample.rsserver.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.rsserver.entity.User;
import com.sample.rsserver.repo.UserJpaRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
 
    private final UserJpaRepo userJpaRepo;
 
    @GetMapping(value = "/users")
    public List<User> findAllUser() {
    	System.out.println("user!!!!");
        return userJpaRepo.findAll();
    }
}