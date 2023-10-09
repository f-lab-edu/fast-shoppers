package com.fastshoppers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {
    private final Logger logger = LoggerFactory.getLogger("LogController의 log");

    @GetMapping("/log")
    public void log() {
        logger.debug("logging!");
    }
}
