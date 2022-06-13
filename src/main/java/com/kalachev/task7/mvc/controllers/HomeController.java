package com.kalachev.task7.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/", "/homepage" })
public class HomeController {

  @GetMapping
  public String home() {
    return "home";
  }

}
