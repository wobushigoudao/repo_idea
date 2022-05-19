package com.lagou.domain;

/**
 *
 */
public class PromotionAdVo {

    //当前页
    private Integer CurrentPage;

    //每页显示的条数
    private Integer PageSize;

    public Integer getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        CurrentPage = currentPage;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }
}
