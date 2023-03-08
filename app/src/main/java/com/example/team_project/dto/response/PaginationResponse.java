package com.example.team_project.dto.response;

public class PaginationResponse<T> {
    private T data;
    private int totalPage;
    private long totalRow;

    public PaginationResponse() {
    }

    public PaginationResponse(T data, int totalPage, long totalRow) {
        this.data = data;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }
}
