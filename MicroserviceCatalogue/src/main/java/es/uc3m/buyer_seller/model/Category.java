package es.uc3m.buyer_seller.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="categories")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
@NamedQuery(name="Category.findByName",  query="SELECT c FROM Category c WHERE c.name LIKE :name")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	@Column(name="parent_id")
	private int parentId;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}