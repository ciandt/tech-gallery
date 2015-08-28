package com.ciandt.techgallery.persistence.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

/**
 * BaseEntity entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
public class BaseEntity<ID> {

	@Id
	ID id;

	@Unindex
	Date inactivatedDate;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Date getInactivatedDate() {
		return inactivatedDate;
	}

	public void setInactivatedDate(Date inactivatedDate) {
		this.inactivatedDate = inactivatedDate;
	}

}
