package com.shadab.expensetrackerapi.resources;

import com.shadab.expensetrackerapi.domain.User;
import com.shadab.expensetrackerapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String,Object> reqMap){
        String firstName =  (String)reqMap.get("firstName");
        String lastName =  (String)reqMap.get("lastName");
        String email =  (String)reqMap.get("email");
        String password =  (String)reqMap.get("password");
        User user = userService.registerUser(firstName,lastName,email,password);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("message","registered successfully");
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> reqMap){
        String email = (String)reqMap.get("email");
        String password = (String)reqMap.get("password");
        User user = userService.validate(email,password);
        Map<String,String> responseMap = new HashMap<>();
        responseMap.put("message","login successfull");
        return new ResponseEntity<>(responseMap,HttpStatus.OK);
    }
}
