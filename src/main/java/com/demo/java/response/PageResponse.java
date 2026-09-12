package com.demo.java.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> {

    private List<T> content;

    private int currentPage;
    private int pageSize;

    private long totalElements;
    private int totalPages;

    private boolean first;
    private boolean last;

    private boolean hasNext;
    private boolean hasPrevious;

    public PageResponse() {
    }

    public PageResponse(List<T> content, int currentPage, boolean first, boolean hasNext, boolean hasPrevious,
            boolean last, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.first = first;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.last = last;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirst() {
        return this.first;
    }

    public boolean getFirst() {
        return this.first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return this.last;
    }

    public boolean getLast() {
        return this.last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public boolean getHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return this.hasPrevious;
    }

    public boolean getHasPrevious() {
        return this.hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

}
