package com.saugat.userservice.resource;

import lombok.*;

import java.io.Serializable;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestObj implements Serializable {
    private int pageNumber;
    private int pageSize;

    @Override
    public String toString() {
        return "PageRequestObj{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
