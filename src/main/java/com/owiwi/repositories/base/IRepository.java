package com.owiwi.repositories.base;

import com.owiwi.model.Entity;

import java.util.List;

public interface IRepository<K, T extends Entity<K>> {

	/**
	 * @return the whole data set.
	 */
	List<T> getAll();

	/**
	 * get all by id
	 * @param id the id of the entity.
	 * @return the list of entity objects.
	 */
	List<T> getAllById(K id);

}
