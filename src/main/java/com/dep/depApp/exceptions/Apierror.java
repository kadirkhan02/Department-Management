package com.dep.depApp.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class Apierror {
    private HttpStatus status;
    private String message;
    private List<String> subErrors;

}
