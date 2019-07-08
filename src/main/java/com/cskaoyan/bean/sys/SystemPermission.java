package com.cskaoyan.bean.sys;

import java.util.List;

public class SystemPermission {
    String id;
    String label;
    List<TypePermission> children;

    public SystemPermission() {
    }

    public SystemPermission(String id, String label, List<TypePermission> children) {
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

    public List<TypePermission> getChildren() {
        return children;
    }

    public void setChildren(List<TypePermission> children) {
        this.children = children;
    }
}
