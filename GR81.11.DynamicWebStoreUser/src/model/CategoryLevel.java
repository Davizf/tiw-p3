package model;

import java.util.LinkedList;

public class CategoryLevel {

	protected int id, parentId, depth;
	protected String name;
	protected LinkedList<CategoryLevel> childs;

	public CategoryLevel(int id, int parentId, String name, int depth) {
		this.id = id;
		this.parentId = parentId;
		this.depth = depth;
		this.name = name;
		childs = new LinkedList<>();
	}

	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
	}

	public int getDepth() {
		return depth;
	}

	public String getName() {
		return name;
	}

	public LinkedList<CategoryLevel> getChilds() {
		return childs;
	}

	@Override
	public String toString() {
		return "CategoryLevel [id=" + id + ", parentId=" + parentId + ", depth=" + depth + ", name=" + name + "]";
	}

}