package com.petstore.demo.controllers

import com.petstore.demo.model.DB
import com.petstore.demo.model.Order
import com.petstore.demo.model.notValid
import com.petstore.demo.model.validateAuthToken
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
class Orders {
    @PostMapping("/orders")
    fun placeOrder(@RequestBody order: Order, @RequestHeader("Authenticate", required = true) header: String) {
        validateAuthToken(header)

        if(!DB.reservePetsFor(order))
            throw HttpClientErrorException(HttpStatus.CONFLICT)

        DB.addOrder(order)
    }

    @GetMapping("/orders/order/{id}")
    fun getOrder(@PathVariable("id") id: Int) = DB.getOrder(id)

    @DeleteMapping("/orders/order/{id}")
    fun deleteOrder(@PathVariable("id") id: Int, @RequestHeader("Authenticate", required = true) header: String) {
        validateAuthToken(header)

        DB.deleteOrder(id)
    }

    @PostMapping("/orders/order/{id}")
    fun updateOrderStatus(@PathVariable("id") id: Int, @RequestBody status: String, @RequestHeader("Authenticate", required = true) header: String) {
        validateAuthToken(header)

        DB.updateOrderStatus(id, status)
    }

    @GetMapping("/orders")
    fun search(@RequestParam(name="status", required=false) status: String?,
               @RequestParam(name="type", required=false) type: String?) = DB.findOrders(status, type)
}
