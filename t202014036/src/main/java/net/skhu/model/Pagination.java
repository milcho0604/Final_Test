package net.skhu.model;

import lombok.Data;

@Data
public class Pagination {
    int pg = 1;        // 현재 페이지
    int sz = 10;       // 페이지 당 레코드 수
    int recordCount;   // 전체 레코드 수
    String title; // 검색할 타이틀 선언

    public String getQueryString() {
        return String.format("pg=%d&sz=%d&title=%s", pg, sz, title);
    }
}
