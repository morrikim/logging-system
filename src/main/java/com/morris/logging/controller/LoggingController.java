package com.morris.logging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class LoggingController {

    @GetMapping("/")
    public String mainPage() {
        return "Hello Logging Main Page";
    }

    @GetMapping("/logging")
    public ResponseEntity writeLogging() {
        String body = "Write Log";
        log.info(body+"Info");
        log.warn(body+"Warn");
        log.error(body+"Error");
        log.debug(body+"Debugger");
        log.trace(body+"trace");
        return new ResponseEntity( body,HttpStatus.OK);
    }

    @GetMapping("/exception")
    public ResponseEntity<String> exeception() {
        try {
            throw new RuntimeException("Morris Throw Exception ");
        }catch(Exception exception) {
            String errorStack = Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\\r\\n"));
            log.error("Morris ExceptionMsg :"+exception.getMessage()+", MorrisExceptionStack : "+errorStack);
            return new ResponseEntity( exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
