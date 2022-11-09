package org.example.enums;

import lombok.Data;
public enum Variant {
    A,B,C,D;
    private String uzName;
    private String engName;

    public String getUzName() {
        return uzName;
    }

    public void setUzName(String uzName) {
        this.uzName = uzName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }
}
