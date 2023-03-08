package com.example.team_project.dto.request;

public class PageableRequest {
    private int offSet;
    private int size;
    private String field;
    private String sortType;

    public PageableRequest() {
    }

    public PageableRequest(int offSet, int size, String field, String sortType) {
        this.offSet = offSet;
        this.size = size;
        this.field = field;
        this.sortType = sortType;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
