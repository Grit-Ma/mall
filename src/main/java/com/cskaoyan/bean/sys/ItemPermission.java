package com.cskaoyan.bean.sys;

public class ItemPermission {
    String id;
    String api;
    String label;

    public ItemPermission() {
    }

    public ItemPermission(String id, String api, String label) {
        this.id = id;
        this.api = api;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
