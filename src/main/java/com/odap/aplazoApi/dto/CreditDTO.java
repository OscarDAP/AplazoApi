package com.odap.aplazoApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreditDTO {

    private Integer payment_number;
    private Double payment_amount;
    private Double pending_amount;
    private LocalDate payment_date;

    /**
     *
     * @param payment_number
     * @param payment_amount
     * @param pending_amount
     * @param payment_date
     */
    public CreditDTO(Integer payment_number, Double payment_amount, Double pending_amount, LocalDate payment_date) {
        this.payment_number = payment_number;
        this.payment_amount = payment_amount;
        this.pending_amount = pending_amount;
        this.payment_date = payment_date;
    }
}
