package com.loststars.tmallboot.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;

public class PageNavigator<T> {

    //页面对象
    @JsonIgnore
    private PageInfo<?> pageInfo;
    
    //数据集合
    private List<T> content;
    
    //显示的页码数组长度
    private int navigatePages;
    
    //显示的页码数组
    private int[] navigatepageNums;
    
    //总页码数量
    private int totalPages;
    
    //当前页码
    private int number;
    
    //所有数据数目
    private int totalElements;
    
    //一页标称数据数量
    private int size;
    
    //一页实际数据数量
    private int numberOfElements;
    
    private boolean isHasContent;
    
    private boolean first;
    
    private boolean last;
    
    private boolean isHasNext;
    
    private boolean isHasPrevious;
    
    public PageNavigator() {
        
    }
    
    public PageNavigator(PageInfo<?> pageInfo, List<T> content, int navigatePages) {
        this.pageInfo = pageInfo;
        this.content = content;
        totalPages = pageInfo.getPages();
        this.navigatePages = Math.min(navigatePages, totalPages);
        number = pageInfo.getPageNum();
        createPages();
        totalElements = (int) pageInfo.getTotal();
        size = pageInfo.getPageSize();
        if (content == null)
            numberOfElements = 0;
        else
            numberOfElements = content.size();
        isHasContent = false;
        if (content != null && content.size() != 0) isHasContent = true;
        first = pageInfo.isIsFirstPage();
        last = pageInfo.isIsLastPage();
        isHasNext = pageInfo.isHasNextPage();
        isHasPrevious = pageInfo.isHasPreviousPage();
    }
    
    private void createPages() {
        int[] pages = null;
        if (number < 1 || number > totalPages) {
            int n = navigatePages;
            pages = new int[n];
            for (int i = 1; i <= n; ++ i) pages[i - 1] = i;
        } else {
            int n = navigatePages;
            pages = new int[n];
            -- n;
            int leftNum = n / 2;
            int rightNum = n - leftNum;
            int left = number - leftNum;
            int right = number + rightNum;
            if (left < 1) {
                right += (1 - left);
                left = 1;
            } else {
                if (right > totalPages) {
                    left -= (right - totalPages);
                    right = totalPages;
                }
            }
            int index = 0;
            for (int i = left; i <= right; ++ i) pages[index ++] = i;
        }
        navigatepageNums = pages;
    }
    
    public PageInfo<?> getPageInfo() {
        return pageInfo;
    }
    
    public void setPage(PageInfo<?> pageInfo) {
        this.pageInfo = pageInfo;
    }
    
    public List<T> getContent() {
        return content;
    }
    
    public void setContent(List<T> content) {
        this.content = content;
    }
    
    public int getNavigatePages() {
        return navigatePages;
    }
    
    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }
    
    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }
    
    public void setNavigatePageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isHasContent() {
        return isHasContent;
    }

    public void setHasContent(boolean isHasContent) {
        this.isHasContent = isHasContent;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isHasNext() {
        return isHasNext;
    }

    public void setHasNext(boolean isHasNext) {
        this.isHasNext = isHasNext;
    }

    public boolean isHasPrevious() {
        return isHasPrevious;
    }

    public void setHasPrevious(boolean isHasPrevious) {
        this.isHasPrevious = isHasPrevious;
    }
}
