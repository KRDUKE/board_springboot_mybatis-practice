package com.dukefirstboard.board.board.board.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String index(){
        System.out.println("HomeController.index");
        return "index";
    }
}
