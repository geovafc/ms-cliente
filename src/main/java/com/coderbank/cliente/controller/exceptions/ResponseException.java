package com.coderbank.cliente.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseException {
    private Integer status;
    private Long timeStamp;
    private String message;
}
