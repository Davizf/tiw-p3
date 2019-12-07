package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HierarchicalCategories {

	private static final int ROOT = 0;
	private static final String FORMAT = "%name\n";//id=%id, name=%name

	private LinkedList<CategoryLevel> categories;
	private String tab, tab2;

	public HierarchicalCategories(ArrayList<Category> categories, String tab, String tab2) {
		this.categories = new LinkedList<>();
		this.tab = tab;
		this.tab2 = tab2;

		// Copy list
		ArrayList<Category> cs=new ArrayList<>(categories);
		for (int i = 0; i < cs.size(); i++) {
			// If inserted, delete
			if (insertInside(cs.get(i), this.categories, 0)) {
				cs.remove(i--);
			}
		}
	}

	public static String getIdChildsStr(CategoryLevel category) {
		List<Integer> list=getIdChilds(category);
		String resul="";
		for (Integer i : list) resul+=i+",";
		return resul.substring(0, resul.length()-1);
	}
	public static List<Integer> getIdChilds(CategoryLevel category) {
		List<Integer> list=new ArrayList<Integer>();
		list.add(category.id);
		return getIdChilds(category, list);
	}
	private static List<Integer> getIdChilds(CategoryLevel category, List<Integer> list) {
		for (CategoryLevel c : category.childs) {
			list.add(c.id);
			getIdChilds(c, list);
		}
		return list;
	}

	public LinkedList<CategoryLevel> getCategories() {
		return categories;
	}

	public ArrayList<Category> getCategoriesOrdered() {
		ArrayList<Category> l=new ArrayList<>();
		insert(l, categories);
		return l;
	}

	private void insert(ArrayList<Category> l, LinkedList<CategoryLevel> categories) {
		Category nc;
		for (CategoryLevel c : categories) {
			nc=new Category();
			nc.setId(c.id);
			nc.setParentId(c.parentId);
			nc.setName(c.name);
			l.add(nc);
			insert(l, c.childs);
		}
	}

	public String getLineOfId(int id) {
		return getLineOfId(id, categories);
	}

	private String getLineOfId(int id, LinkedList<CategoryLevel> categories) {
		for (CategoryLevel c : categories) {
			if (c.id == id)
				return line(c);
			String line = getLineOfId(id, c.childs);
			if (line != null) return line;
		}
		return null;
	}

	private boolean insertInside(Category c, LinkedList<CategoryLevel> categories, int depth) {
		// Base case, insert on root
		if (c.getParentId() == ROOT) {
			this.categories.add(new CategoryLevel(c.getId(), c.getParentId(), c.getName(), depth));
			return true;
		}
		for (int i = 0; i < categories.size(); i++) {
			// Base case, insert on parent
			if (c.getParentId() == categories.get(i).id) {
				categories.get(i).childs.add(new CategoryLevel(c.getId(), c.getParentId(), c.getName(), depth+1));
				return true;
			}
			if (insertInside(c, categories.get(i).childs, depth+1))
				return true;
		}
		return false;
	}

	private static String formatedLine(CategoryLevel c) {
		return FORMAT.replace("%id", c.id+"").replace("%name", c.name);
	}

	private String line(CategoryLevel c) {
		String resul="";
		for (int i = 0; i < c.depth; i++) {
			if (i == c.depth-1)
				resul+=tab;
			else
				resul+=tab2;
		}
		resul += formatedLine(c);
		return resul;
	}

	private String lineRecursive(CategoryLevel c) {
		String resul = line(c);

		for (CategoryLevel c2 : c.childs)
			resul+=lineRecursive(c2);

		return resul;
	}

	@Override
	public String toString() {
		StringBuilder resul = new StringBuilder();

		for (CategoryLevel c : categories)
			resul.append(lineRecursive(c));

		return resul.toString();
	}

}