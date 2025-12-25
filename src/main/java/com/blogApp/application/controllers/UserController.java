package com.blogApp.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.application.payloads.ApiResponce;
import com.blogApp.application.payloads.UserDTO;
import com.blogApp.application.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("newUser")
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDto) {
		UserDTO createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	@GetMapping("/getUser/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
		UserDTO userDto = this.userService.getUserById(userId);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userDtos = this.userService.getAllUsers();
		return new ResponseEntity<>(userDtos, HttpStatus.OK);
	}

	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDto, @PathVariable Integer userId) {
		UserDTO updateUserDto = this.userService.UpdateUser(userDto, userId);
		return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponce> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity(new ApiResponce("User deleted successfully", true), HttpStatus.OK);
	}

}