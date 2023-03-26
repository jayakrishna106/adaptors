package com.connectors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIFields {
    private String method;
    private String path;
    private String consumes;
    private String produces;
    private int responseCode;
    private String responseMessage;
    private Exceptions[] exceptions;
    private String to;
    @ToString
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Exceptions{
        private int responseCode;
        private String exception;
    }
}
