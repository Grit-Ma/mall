package com.cskaoyan.bean.sys;

import java.util.List;

public class TypePermission {
    String id;
    String label;
    List<ItemPermission> children;

    public TypePermission() {
    }

    public TypePermission(String id, String label, List<ItemPermission> children) {
        this.id = id;
        this.label = label;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ItemPermission> getChildren() {
        return children;
    }

    public void setChildren(List<ItemPermission> children) {
        this.children = children;
    }
}
