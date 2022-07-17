package com.example.thecollectiveassessment.core.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GetPaginatedResponse<T> {
    List<T> plants;

    boolean moreExists;

    Long offset;
}
