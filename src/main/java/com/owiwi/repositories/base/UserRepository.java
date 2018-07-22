package com.owiwi.repositories.base;

import com.owiwi.data.UserDatabase;
import com.owiwi.model.SearchRequest;
import com.owiwi.model.User;
import java.time.LocalDate;


import java.util.List;
import java.util.stream.Collectors;

public class UserRepository extends BaseRepository<Long, User> {

	public UserRepository() {
		this.dataList = UserDatabase.generateUsers();
	}

	/** return all users **/
	public List<User> getAll() {
		return this.dataList;
	}

	/**
	 * return users based from id
	 *
	 * @param id The id of the entity.
	 * @return
	 */
	public List<User> getAllById(Long id) {
		List<User> users = this.dataList;
		return users.stream()
				.filter(user -> user.getId() == id)
				.collect(Collectors.toList());

	}

	/**
	 * return users based on search filters
	 *
	 * @param searchRequest
	 * @param requestingUser
	 * @return
	 */
	public List<User> getUsersByFilters(SearchRequest searchRequest, User requestingUser) {
		List<User> users = this.dataList;

		int requestingUserYear = requestingUser.getDateOfBirth().getYear();

		List<User> filteredUsers = users.stream()
				.filter(user -> user.getEducationLevel().toString().equals(searchRequest.getEducationLevel())) // filter based on education level
				.filter(user -> user.getGender().toString().equals(searchRequest.getGender())) // filter based on gender
				.filter(user -> user.getDateOfBirth().getYear() > requestingUserYear + Integer.parseInt(searchRequest.getAge()) // filter based on age older
				|| user.getDateOfBirth().getYear() < requestingUserYear - Integer.parseInt(searchRequest.getAge())) // filter based on age younger
				.collect(Collectors.toList());

		return filteredUsers;
	}
}
