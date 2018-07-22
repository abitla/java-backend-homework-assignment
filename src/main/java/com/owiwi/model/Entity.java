package com.owiwi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;

/**
 * An entity will always have an id and a creation date.
 */
public abstract class Entity<T> {

	private final T id;

	@JsonIgnore
	private ZonedDateTime createdDate; //ignoring in JsonResponse. Too Large object to return in Response. TODO: return appropriate date

	Entity(T id, ZonedDateTime createdDate) {
		this.id = id;
		this.createdDate = createdDate;
	}

	public T getId() {
		return id;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}
}
