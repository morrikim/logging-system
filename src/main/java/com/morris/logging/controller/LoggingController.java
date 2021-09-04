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

    @GetMapping("/logging")
    public ResponseEntity<?> writeLogging() {
        String body = "Write Log";
        log.info(body);
        log.warn(body);
        log.error(body);
        log.debug(body);
        log.trace(body);
        return new ResponseEntity( body,HttpStatus.OK);
    }

    @GetMapping("/exception")
    public ResponseEntity<?> exeception() {
        String body = "Exception";
        try {
            throw new RuntimeException("Morris Throw Exception ");
        }catch(Exception exception) {
            String errorStack = Arrays.stream(exception.getStackTrace()).map((e) -> {
                return e.toString();
            }).collect(Collectors.joining("\\r\\n"));
            log.error("Morris ExceptionMsg :"+exception.getMessage()+", MorrisExceptionStack : "+errorStack);
            return new ResponseEntity( exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
