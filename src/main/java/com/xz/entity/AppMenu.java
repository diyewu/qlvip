package com.xz.entity;

import java.util.List;

public class AppMenu {
	private String id;
	private String parent_id;
	private String menuName;
	private boolean leaf;
	private boolean checked;
	private List<AppMenu> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<AppMenu> getChildren() {
		return children;
	}
	public void setChildren(List<AppMenu> children) {
		this.children = children;
	}
	
}
