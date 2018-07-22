package com.owiwi.service;

import com.owiwi.model.SearchRequest;
import com.owiwi.model.User;
import com.owiwi.model.Connection;
import com.owiwi.repositories.base.ConnectionRepository;
import com.owiwi.repositories.base.UserRepository;
import com.owiwi.enumeration.ConnectionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	ConnectionRepository connectionRepository = new ConnectionRepository();
	UserRepository userRepository = new UserRepository();

	@Override
	public User getUser(long id){
		List<User> users = userRepository.getAllById(id);
		return (users != null ? users.get(0) : null);
	}

	@Override
	public List<Connection> getConnections(long id){
		return connectionRepository.getAllById(id);
	}

	@Override
	public void addConnections(ConnectionType connectionType, List<User> users){
		connectionRepository.addConnections(connectionType, users);

	}

	@Override
	public List<User> getUsersByFilters(SearchRequest searchRequest, User user) {
		return userRepository.getUsersByFilters(searchRequest, user);
	}


}