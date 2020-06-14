package com.leverxblog.filtration;


import lombok.Getter;

@Getter
public class Page {
    private Integer skip;
    private Integer limit;

    public Page(Integer skip, Integer limit) {
        this.skip = skip;
        this.limit = limit;
    }
}
