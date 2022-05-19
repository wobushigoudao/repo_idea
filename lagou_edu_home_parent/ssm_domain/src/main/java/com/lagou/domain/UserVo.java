package com.lagou.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *
 */
public class UserVo {
    private Integer currentPage;
    private Integer pageSize;
    //多条件查询：用户名（手机号）
    private String username;
    //注册起始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //设置springmvc默认的转成Date的类型格式为yyyy-MM-dd，即只会使用这个了
    //只要是字符串，那么要变成Date类型，基本都要进行转换
    private Date startCreateTime;
    //注册结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //使用注解，那么就与配置文件是一样的，配置文件指定了对应类，而注解直接在这个类中指定了对应的类型
    private Date endCreateTime;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
