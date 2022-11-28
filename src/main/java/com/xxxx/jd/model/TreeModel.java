package com.xxxx.jd.model;

public class TreeModel {
    private Integer id; //资源的id
    private Integer pId;    //父资源的id
    private String name;    //资源名
    private boolean checked = false;    //复选框是否被勾选，默认不勾选

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
