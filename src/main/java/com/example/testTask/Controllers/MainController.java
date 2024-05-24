package com.example.testTask.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/newDelivery")
    public String newDelivery(){
        return "new_delivery";
    }
}
