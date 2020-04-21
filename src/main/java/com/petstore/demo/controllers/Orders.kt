package com.petstore.demo.controllers

import com.petstore.demo.model.DB
import com.petstore.demo.model.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
class Orders {
    @PostMapping("/orders")
    fun placeOrder(@RequestBody order: Order) {
        if(!DB.reservePetsFor(order))
            throw HttpClientErrorException(HttpStatus.CONFLICT) as Throwable

        DB.addOrder(order)
    }

    @GetMapping("/orders/order/{id}")
    fun getOrder(@PathVariable("id") id: Int) = DB.getOrder(id)

    @DeleteMapping("/orders/order/{id}")
    fun deleteOrder(@PathVariable("id") id: Int) = DB.deleteOrder(id)

    @PostMapping("/orders/order/{id}")
    fun updateOrderStatus(@PathVariable("id") id: Int, @RequestBody status: String) {
        DB.updateOrderStatus(id, status)
    }

    @GetMapping("/orders")
    fun search(@RequestParam(name="status", required=false) status: String?,
               @RequestParam(name="type", required=false) type: String?) = DB.findOrders(status, type)
}
