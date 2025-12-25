package com.blogApp.application.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.application.entities.User;
import com.blogApp.application.exceptions.ResourceNotFoundException;
import com.blogApp.application.payloads.UserDTO;
import com.blogApp.application.repositories.UserRepo;
import com.blogApp.application.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDto) {

		User user = this.userRepo.save(this.dtoToUser(userDto));
		return this.userToDto(user);
	}

	@Override
	public UserDTO UpdateUser(UserDTO userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		return this.userToDto(this.userRepo.save(user));
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users =  this.userRepo.findAll();
		return users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDTO userDto) {
		return this.modelMapper.map(userDto, User.class);
	}

	private UserDTO userToDto(User user) {
		return this.modelMapper.map(user, UserDTO.class);
	}
}