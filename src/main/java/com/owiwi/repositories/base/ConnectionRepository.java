package com.owiwi.repositories.base;

import com.owiwi.data.ConnectionDatabase;
import com.owiwi.enumeration.ConnectionType;
import com.owiwi.model.Connection;
import com.owiwi.model.SearchRequest;
import com.owiwi.model.User;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ConnectionRepository extends BaseRepository<Long, Connection> {

	public ConnectionRepository() {
		ConnectionDatabase connectionDatabase = new ConnectionDatabase();
		this.dataList = connectionDatabase.generateConnections();
	}

	/** return list of all connections **/
	public List<Connection> getAll() {

		return this.dataList;
	}

	/**
	 * return connection from id
	 *
	 * @param id The id of the entity.
	 * @return
	 */
	public List<Connection> getAllById(Long id) {

		return this.dataList.stream()
				.filter(connection -> connection.getId() == id)
				.collect(Collectors.toList());
	}

	/**
	 * add new connections for users for provided connection type
	 *
	 * @param connectionType - connection type of the users
	 * @param users - list of users that require a new connection
	 */
	public void addConnections(ConnectionType connectionType, List<User> users) {
		for (int a = 0; a < users.size(); a++) {
			for (int b = users.size() - 1; b >= 0; b--) {
				if (a != b) {
					this.dataList.add(new Connection((long)users.get(a).getId(),
							(long)users.get(a).getId(),
							(long)users.get(b).getId(),
							connectionType,
							ZonedDateTime.now()));
				}
			}
		}
	}


}
