package com.example.thecollectiveassessment.core.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class PaginationDto {

    @JsonProperty("limit")
    int limit;

    @JsonProperty("offset")
    int offset;

    public PaginationDto(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public PaginationDto() {
        this.offset = 0;
        this.limit = 0;
    }
}
