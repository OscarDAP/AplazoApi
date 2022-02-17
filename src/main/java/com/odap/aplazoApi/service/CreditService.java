package com.odap.aplazoApi.service;

import com.odap.aplazoApi.dto.CreditDTO;
import com.odap.aplazoApi.dto.RequestDTO;
import com.odap.aplazoApi.dto.ResponseDTO;
import com.odap.aplazoApi.entity.CreditDetails;
import com.odap.aplazoApi.entity.Requests;
import com.odap.aplazoApi.repository.CreditDetailsRepository;
import com.odap.aplazoApi.repository.RequestRepository;
import com.odap.aplazoApi.util.Util;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditService {

    public RequestRepository requestRepository;
    public CreditDetailsRepository creditDetailsRepository;

    public CreditService(
            RequestRepository requestRepository,
            CreditDetailsRepository creditDetailsRepository
    ){
        this.requestRepository = requestRepository;
        this.creditDetailsRepository  = creditDetailsRepository;
    }


    private ResponseDTO<String> validateRequest(RequestDTO request) {
        ResponseDTO<String> response = new ResponseDTO();

        if (!Util.isBetween(request.getTerms(), 4, 52)) {
            response.addDetails("Invalid term, expected 4 - 52");
        }

        if (!Util.isGreaterThanAndLessThan(request.getRate(), 1.0, 100.00)) {
            response.addDetails("Invalid rate, expected > 1.00% and < 100.00%");
        }

        if (!Util.isGreaterThanAndLessThan(request.getAmount(), 1.0, 999999.00)) {
            response.addDetails("Invalid amount, expected > $1.00 and < $999,999.00");
        }

        return response;
    }

    /**
     *  Calculate the sample interest
     *
     * @param request
     *
     * @return if all is ok then return <tt>ResponseDTO{@literal <CreditDTO>}</tt> with payment details otherwise <tt>ResponseDTO{@literal <String>} </tt> with error details
     */

    public ResponseDTO calculate(RequestDTO request) {
        ResponseDTO<String> error = this.validateRequest(request);
        if (error.getDetails().isEmpty()) {
            try {
                return new ResponseDTO<CreditDTO>(
                        Util.STATUS.SUCCESS.name(),
                        this.calculateAmortization(request.getAmount(), request.getRate(), request.getTerms())
                );
            } catch (Exception e) {
                error.setMessage(e.getMessage());
                error.setStatus(Util.STATUS.ERROR.name());
            }
        } else {
            error.setStatus(Util.STATUS.INVALID.name());
        }
        return error;
    }


    /**
     *
     * Calculate the sample interest and generate the payment list
     *
     * @param amount original amount
     * @param rate rate of interest from 1% to 100%
     * @param period number of periods
     * @return <tt>ResponseDTO{@literal <CreditDTO>}</tt> Payment details list
     */
    public List<CreditDTO> calculateAmortization(Double amount, Double rate, Integer period) {
        List<CreditDTO> payments = new ArrayList<>();
        Double amortization = calculateAmortization(amount, period);
        LocalDate date = LocalDate.now();
        rate = (rate/100);
        for (int i = 1; i <= period; i++) {
            Double interest = amount * rate;
            Double payment = amortization + interest;
            amount -= amortization;//pending
            payments.add(new CreditDTO(i, payment, amount, date.plusWeeks(i)));
        }

        return payments;
    }

    /**
     * calculate the amortization from amount and number of periods
     *
     * @param amount original amount
     * @param numPeriods number of periods
     * @return amortization
     */
    public Double calculateAmortization(Double amount, Integer numPeriods) {
        return amount / numPeriods;
    }

    /**
     * Calculate the simple interest
     * @param amount original amount
     * @param rate  value from 1% to 100%
     * @param period number of periods
     * @return simple interest
     */
    public Double calculateInterest(Double amount, Double rate, Integer period) {
        return amount * (rate / 100) * period;
    }

    /**
     * Calculate the total amount (original amount + interest)
     *
     * @param amount original amount
     * @param rate value from 1% to 100%
     * @param period number of periods
     * @return total amount
     */
    public Double calculateTotalAmount(Double amount, Double rate, Integer period) {
        return amount + calculateInterest(amount, rate, period);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveCredit(RequestDTO request,List<CreditDTO> details){

        Requests newRequest = new Requests();
        newRequest.setAmount(request.getAmount());
        newRequest.setRate(request.getRate());
        newRequest.setTerms(request.getTerms());
        newRequest.setInterestGenerated(this.calculateInterest(request.getAmount(),request.getRate(),request.getTerms()));
        this.requestRepository.save(newRequest);
        this.saveCreditDetails(newRequest.getId(),details);

        //this.requestRepository.findAll().forEach(System.out::println);
        //this.creditDetailsRepository.findAllByRequestId(newRequest.getId()).forEach(System.out::println);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveCreditDetails(Long requestId,List<CreditDTO> details) {

        List<CreditDetails> creditDetails = details.stream().map(d->{
            CreditDetails detail = new CreditDetails();
            detail.setRequestId(requestId);
            detail.setPayment_number(d.getPayment_number());
            detail.setPayment_amount(d.getPayment_amount());
            detail.setPending_amount(d.getPending_amount());
            detail.setPayment_date(d.getPayment_date());
            return detail;
        }).collect(Collectors.toList());
        this.creditDetailsRepository.saveAll(creditDetails);

    }



}
