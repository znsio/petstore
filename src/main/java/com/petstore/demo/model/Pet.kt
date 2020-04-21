package com.petstore.demo.model

import java.util.concurrent.atomic.AtomicInteger

data class Pet(val name: String = "", val type: String = "", var status: String = "available", val id: Int = idGenerator.getAndIncrement()) {
    fun newPet(name: String?, type: String?, status: String?) =
            Pet(name ?: this.name, type ?: this.type, status ?: this.status)

    companion object IDFactory {
        val idGenerator: AtomicInteger = AtomicInteger()
    }
}
