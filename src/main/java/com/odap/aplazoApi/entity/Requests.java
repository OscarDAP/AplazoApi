package com.odap.aplazoApi.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Requests")
public class Requests {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double amount;

    private Integer terms;

    private Double rate;

    private Double interestGenerated;

    @Override
    public String toString() {
        return "Requests{" +
                "id=" + id +
                ", amount=" + amount +
                ", terms=" + terms +
                ", rate=" + rate +
                ", interestGenerated=" + interestGenerated +
                '}';
    }
}
