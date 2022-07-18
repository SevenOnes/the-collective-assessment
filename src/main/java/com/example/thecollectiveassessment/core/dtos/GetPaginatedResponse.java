package com.example.thecollectiveassessment.core.dtos;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetPaginatedResponse<T> {
    List<T> plants;

    boolean moreExists;

    Long offset;
}
