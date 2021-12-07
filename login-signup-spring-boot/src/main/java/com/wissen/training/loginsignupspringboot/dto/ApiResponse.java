package com.wissen.training.loginsignupspringboot.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Value;

@Value
public class ApiResponse {
    Boolean success;
    String message;
}
