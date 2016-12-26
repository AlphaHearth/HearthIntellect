package com.hearthintellect.utils;

/**
 * Bean class describing a page of entities.
 */
public class Page {

    private int pageNum;
    private int numPerPage;

    public Page() {}

    public Page(int pageNum, int numPerPage) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getOffset() {
        return numPerPage * (pageNum - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (pageNum != page.pageNum) return false;
        return numPerPage == page.numPerPage;

    }

    @Override
    public int hashCode() {
        int result = pageNum;
        result = 31 * result + numPerPage;
        return result;
    }
}
