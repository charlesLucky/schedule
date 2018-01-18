/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ban
 */
@Controller
public class CommonController {

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/")
    public String index1() {
        return "index";
    }

    @RequestMapping("/list")
    public String list() {
        return "list";
    }

    @RequestMapping("/running")
    public String running() {
        return "running";
    }

    @RequestMapping("plan")
    public String plan() {
        return "plan";
    }

    @RequestMapping("/loginCheck")
    public String loginCheck(String username, String password, HttpSession session) {
        if ("admin".equalsIgnoreCase(username)
                && "admin".equalsIgnoreCase(password)) {
            session.setAttribute("user", username);
        }
        return "redirect:index";
    }
}
