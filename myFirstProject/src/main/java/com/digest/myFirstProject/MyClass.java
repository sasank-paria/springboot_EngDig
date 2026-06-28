package com.digest.myFirstProject;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyClass {

    @GetMapping("hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("hi")
    public String sayHi() {
        return "hiii";
    }
}