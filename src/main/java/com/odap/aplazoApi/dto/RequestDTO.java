package com.odap.aplazoApi.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {

    private Double amount;
    private Integer terms;
    private Double rate;

}
