package com.example.thecollectiveassessment.core.dtos;

import com.example.thecollectiveassessment.core.enums.OrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class GetPlantByLocationDto extends PaginationDto {

    @JsonProperty("limit")
    String location;

    @JsonProperty("orderBy")
    OrderBy orderBy;

    public String toString() {
        return "GetPlantByLocationDto ["
                + "location=" + location
                + ", orderBy=" + orderBy
                + ", limit=" + limit
                + ", offset=" + offset
                + "]";
    }
}
