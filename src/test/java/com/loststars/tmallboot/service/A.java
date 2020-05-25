package com.loststars.tmallboot.service;

import org.springframework.stereotype.Component;

@Component
public class A {

    public void printA() {
        System.out.println(this.getClass().toString());
        System.out.println("A");
        printB();
    }
    
    public void printB() {
        System.out.println("B");
    }
}
