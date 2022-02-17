package com.odap.aplazoApi.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "CreditDetails")
public class CreditDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer payment_number;

    private Double payment_amount;

    private Double pending_amount;

    private LocalDate payment_date;

    private Long requestId;

    @Override
    public String toString() {
        return "CreditDetails{" +
                "id=" + id +
                ", payment_number=" + payment_number +
                ", payment_amount=" + payment_amount +
                ", pending_amount=" + pending_amount +
                ", payment_date=" + payment_date +
                ", requestId=" + requestId +
                '}';
    }
}
