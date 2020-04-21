package com.petstore.demo.model

import java.util.concurrent.atomic.AtomicInteger

class Order(val type: String = "", val count: Int = 0, var status: String = "pending", val id: Int = idGenerator.getAndIncrement()) {
    companion object {
        val idGenerator: AtomicInteger = AtomicInteger()
    }
}
