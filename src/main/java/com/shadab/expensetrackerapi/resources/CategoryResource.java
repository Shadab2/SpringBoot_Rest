package com.shadab.expensetrackerapi.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    @GetMapping
    public String getAllCategories(HttpServletRequest req){
        int userId = (Integer) req.getAttribute("userId");
        return "Authenticated : "+" Welcome User "+userId;
    }
}
