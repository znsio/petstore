package com.petstore.demo.controllers

import com.petstore.demo.model.Creds
import com.petstore.demo.model.DB
import com.petstore.demo.model.authToken
import com.petstore.demo.model.credsAreValid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.lang.Exception

@RestController
class Auth {
    @PostMapping("/auth")
    fun authenticate(@RequestBody creds: Creds): String {
        if(credsAreValid(creds))
            return authToken

        throw Exception("Invalid credentials")
    }
}
