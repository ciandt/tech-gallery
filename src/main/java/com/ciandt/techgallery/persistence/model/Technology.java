package com.ciandt.techgallery.persistence.model;

/**
 * Technology entity.
 * 
 * @author Felipe Goncalves de Castro
 *
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class Technology extends BaseEntity<Long> {

	@Index
	private String name;
	
	@Unindex
	private String description;
	
	@Unindex
	private String website;
	
	@Unindex
	private String author;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
