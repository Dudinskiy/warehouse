package org.example.warehouse.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @Secured({"ROLE_Администратор", "ROLE_Кладовщик", "ROLE_Сотрудник"})
    @GetMapping()
    public ModelAndView home(){
        return new ModelAndView("home");
    }

    @Secured({"ROLE_Администратор", "ROLE_Кладовщик", "ROLE_Сотрудник"})
    @GetMapping("reference")
    public ModelAndView reference(){
        return new ModelAndView("referencePage");
    }
}
