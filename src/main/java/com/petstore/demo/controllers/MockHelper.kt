package com.petstore.demo.controllers

import com.petstore.demo.model.DB.addOrder
import com.petstore.demo.model.DB.addPet
import com.petstore.demo.model.DB.cleanSlate
import com.petstore.demo.model.Order
import com.petstore.demo.model.Pet
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@ConditionalOnProperty(name=["environment"], havingValue="test")
@RestController
class MockHelper {
    @PostMapping("/_server_state")
    fun serverState(@RequestBody state: HashMap<String, Any>) {
        cleanSlate()

        when {
            state.containsKey("id") -> {
                if(state.getOrDefault("api", "") == "order")
                    addOrder(Order(id = Integer.parseInt(state.get("id").toString()), count = 0, status = "pending", type = "dog"))
                else
                    addPet(newPetById(state, "id"))
            }
            state.containsKey("type") && state["type"] != "" -> {
                val count = state.getOrDefault("count", 2)
                if(count !is Int)
                    throw Exception("Received parameter count=$count but count was not a number.")

                if(state.getOrDefault("api", "") == "order") {
                    repeat(count) {
                        addOrder(Order(type = state["type"].toString(), count = 0, status = "pending"))
                        addPet(Pet(type = state["type"].toString(), name = "Random name", status = "available"))
                    }
                } else {
                    repeat(count) {
                        addPet(Pet(type = state["type"].toString(), name = "Archie", status = "available"))
                    }
                }
            }
            state.containsKey("name") && state["name"] != "" -> {
                addPet(Pet(name = state["name"].toString(), type = "cat", status = "available"))
                addPet(Pet(name = state["name"].toString(), type = "cat", status = "available"))
            }
            state.containsKey("status") && state["status"] != "" -> {
                val count = 2

                if(state.getOrDefault("api", "") == "order") {
                    repeat(count) {
                        addOrder(Order(status = state["status"].toString(), count = 0, type = "dog"))
                    }
                } else {
                    repeat(count) {
                        addPet(Pet(status = state["status"].toString(), name = "Archie", type = "dog"))
                    }
                }
            }
            state.containsKey("no_pets") -> {

            }
            else -> {
                throw Exception("Setup keys not recognized: ${state.keys}")
            }
        }
    }

    private fun newPetById(state: HashMap<String, Any>, key: String) = Pet(name = "Archie", id = state[key].toString().toInt(), type = "dog", status = "available")
}
