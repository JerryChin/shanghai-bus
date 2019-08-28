package io.github.jerrychin.shanghaibus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Jerry Chin
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world!";
    }
}
