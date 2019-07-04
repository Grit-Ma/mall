package com.cskaoyan.bean.sys;

public class Label {
    int value;
    String label;

    public Label() {
    }

    public Label(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Label{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}
