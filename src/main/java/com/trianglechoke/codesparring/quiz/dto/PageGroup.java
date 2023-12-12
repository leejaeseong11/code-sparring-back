package com.trianglechoke.codesparring.quiz.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageGroup<T> {
    public static final Integer CNT_PER_PAGE = 10;
    public static final Integer CNT_PER_PAGEGROUP = 5;
    private List<T> list;
    private Long totalCnt; // 총상품수  11
    private Integer currentPage; // 현재페이지  1 2 3 4
    private Integer totalPage; // 총페이지수 4
    private Integer startPage; // 시작페이지     1 1 3 3

    private Integer endPage; // 끝페이지        2 2 4 4

    public PageGroup(List<T> list, Integer currentPage, Long totalCnt) {
        this.list = list;
        this.currentPage = currentPage;
        this.totalCnt = totalCnt;
        // 총페이지수 계산
        this.totalPage = (int) Math.ceil((double) totalCnt / CNT_PER_PAGE);
        if (currentPage <= totalPage) {
            // 시작페이지, 끝페이지 계산
            this.startPage = (currentPage - 1) / CNT_PER_PAGEGROUP * CNT_PER_PAGEGROUP + 1;
            this.endPage = startPage + CNT_PER_PAGEGROUP - 1;
            if (endPage > totalPage) {
                endPage = totalPage;
            }
        }
    }
}
