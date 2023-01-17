package com.shadab.expensetrackerapi.resources;

import com.shadab.expensetrackerapi.domain.User;
import com.shadab.expensetrackerapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shadab.expensetrackerapi.constants.Constants.API_SECRET_KEY;
import static com.shadab.expensetrackerapi.constants.Constants.TOKEN_VALIDITY;

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
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> reqMap){
        String email = (String)reqMap.get("email");
        String password = (String)reqMap.get("password");
        User user = userService.validate(email,password);
        return new ResponseEntity<>(generateJWTToken(user),HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
