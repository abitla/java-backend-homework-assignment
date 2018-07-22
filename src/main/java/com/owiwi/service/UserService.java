package com.owiwi.service;

import com.owiwi.model.SearchRequest;
import com.owiwi.model.User;
import com.owiwi.model.Connection;
import com.owiwi.enumeration.ConnectionType;

import java.util.List;

public interface UserService {

	// get user by id
	User getUser(long id);

	// get connections of the user by id
	List<Connection> getConnections(long id);

	// add connections based on type
	void addConnections(ConnectionType Type, List<User> users);

	// get users based on filter
	List<User> getUsersByFilters(SearchRequest searchRequest, User user);

}
