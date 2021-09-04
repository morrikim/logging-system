package com.morris.logging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
