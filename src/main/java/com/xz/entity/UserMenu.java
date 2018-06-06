package com.xz.entity;

public class UserMenu {
    private Integer id;

    private Integer parentId;

    private String menuUrl;

    private String menuName;

    private String leaf;

    private Integer button;

    private String authLeaf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf == null ? null : leaf.trim();
    }

    public Integer getButton() {
        return button;
    }

    public void setButton(Integer button) {
        this.button = button;
    }

    public String getAuthLeaf() {
        return authLeaf;
    }

    public void setAuthLeaf(String authLeaf) {
        this.authLeaf = authLeaf == null ? null : authLeaf.trim();
    }
}