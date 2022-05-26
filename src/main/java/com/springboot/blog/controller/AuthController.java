package com.springboot.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dtos.JWTAuthResponse;
import com.springboot.blog.dtos.LoginDTO;
import com.springboot.blog.dtos.SignUpDTO;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticatedUser(@RequestBody LoginDTO loginDTO){
	Authentication authentication = 	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	
	//get token from tokenProvider
	String token = jwtTokenProvider.generateToken(authentication);
	
	return ResponseEntity.ok(new JWTAuthResponse(token));
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
		
		if(userRepository.existsByUsername(signUpDTO.getUsername())) {
			return new ResponseEntity<>("Username é already taken!", HttpStatus.BAD_REQUEST);
		}
		
		if(userRepository.existsByEmail(signUpDTO.getEmail())) {
			return new ResponseEntity<>("Email is already talen!", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setName(signUpDTO.getName());
		user.setEmail(signUpDTO.getEmail());
		user.setUsername(signUpDTO.getUsername());
		user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
		
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();		
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("User registered sucessfully", HttpStatus.OK);
	}				
	

}
