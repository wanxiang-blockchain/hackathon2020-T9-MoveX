package com.example.vehicle.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PageInfoUtils {
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //当前页展示的数据的起始行
    private int startRow;
    //当前页展示的数据的结束行
    private int endRow;
    //总记录数--所需要进行分页的数据条数
    private long total;
    //总页数
    private int pages;
    //页面展示的结果集，比如说当前页要展示20条数据，则此list为这20条数据
    //private List<T> list;
    //前一页页码
    private int prePage;
    //下一页页码
    private int nextPage;
    //是否为第一页，默认为false，是第一页则设置为true
    private boolean isFirstPage ;
    //是否为最后一页默认为false，是最后一页则设置为true
    private boolean isLastPage ;
    //是否有前一页，默认为false，有前一页则设置为true
    private boolean hasPreviousPage ;
    //是否有下一页，默认为false，有后一页则设置为true
    private boolean hasNextPage ;
    //导航页码数，所谓导航页码数，就是在页面进行展示的那些1.2.3.4...
    //比如一共有分为两页数据的话，则将此值设置为2
    private int navigatePages;
    //所有导航页号，一共有两页的话则为[1,2]
    private int[] navigatepageNums;
    //导航条上的第一页页码值
    private int navigateFirstPage;
    //导航条上的最后一页页码值
    private int navigateLastPage;

    public static <T> PageInfo<T> list2PageInfo(List<T> arrayList, Integer pageNum, Integer pageSize) {
        //实现list分页
        PageHelper.startPage(pageNum, pageSize);
        int pageStart = pageNum == 1 ? 0 : (pageNum - 1) * pageSize;
        int pageEnd = arrayList.size() < pageSize * pageNum ? arrayList.size() : pageSize * pageNum;
        List<T> pageResult = new LinkedList<T>();
        if (arrayList.size() > pageStart) {
            pageResult = arrayList.subList(pageStart, pageEnd);
        }
        PageInfo<T> pageInfo = new PageInfo<T>(pageResult);
        //获取PageInfo其他参数
        pageInfo.setTotal(arrayList.size());
        int endRow = pageInfo.getEndRow() == 0 ? 0 : (pageNum - 1) * pageSize + pageInfo.getEndRow() + 1;
        pageInfo.setEndRow(endRow);
        boolean hasNextPage = arrayList.size() <= pageSize * pageNum ? false : true;
        pageInfo.setHasNextPage(hasNextPage);
        boolean hasPreviousPage = pageNum == 1 ? false : true;
        pageInfo.setHasPreviousPage(hasPreviousPage);
        pageInfo.setIsFirstPage(!hasPreviousPage);
        boolean isLastPage = (arrayList.size() > pageSize * (pageNum - 1) && arrayList.size() <= pageSize * pageNum) ? true : false;
        pageInfo.setIsLastPage(isLastPage);
        int pages = arrayList.size() % pageSize == 0 ? arrayList.size() / pageSize : (arrayList.size() / pageSize) + 1;
        pageInfo.setNavigateLastPage(pages);
        int[] navigatePageNums = new int[pages];
        for (int i = 1; i < pages; i++) {
            navigatePageNums[i - 1] = i;
        }
        pageInfo.setNavigatepageNums(navigatePageNums);
        int nextPage = pageNum < pages ? pageNum + 1 : 0;
        pageInfo.setNextPage(nextPage);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPages(pages);
        pageInfo.setPrePage(pageNum - 1);
        pageInfo.setSize(pageInfo.getList().size());
        int starRow = arrayList.size() < pageSize * pageNum ? 1 + pageSize * (pageNum - 1) : 0;
        pageInfo.setStartRow(starRow);
        return pageInfo;
    }
}
