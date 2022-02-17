package com.odap.aplazoApi.controller;

import com.odap.aplazoApi.dto.RequestDTO;
import com.odap.aplazoApi.dto.ResponseDTO;
import com.odap.aplazoApi.service.CreditService;
import com.odap.aplazoApi.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/api/credit")
@RestController
public class Controller {

    private CreditService creditService;

    @Autowired
    public Controller(CreditService creditService){
        this.creditService = creditService;
    }

    @PostMapping(value ="/calculate")
    public  ResponseDTO calculate(@RequestBody RequestDTO data){
        ResponseDTO response = this.creditService.calculate(data);
        if(response.getStatus().equals(Util.STATUS.SUCCESS.name())){
            this.creditService.saveCredit(data,response.getDetails());
        }

        return response;
    }


}
