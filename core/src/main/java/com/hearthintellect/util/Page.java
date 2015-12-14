package com.hearthintellect.util;

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
}
