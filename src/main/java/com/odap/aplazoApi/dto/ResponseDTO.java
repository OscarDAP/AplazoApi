package com.odap.aplazoApi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    protected T data;
    protected List<T> details;
    protected String status;
    protected String message;


    public ResponseDTO(){
        this.details = new ArrayList<T>();
    }

    public ResponseDTO(String status,String message){
       this();
       this.status = status;
       this.message = message;
    }

    public ResponseDTO(String status, List<T> details){
        this();
        this.status = status;
        this.details = details;
    }

    public void addDetails(T detail){
        this.details.add(detail);
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "details=" + details +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
