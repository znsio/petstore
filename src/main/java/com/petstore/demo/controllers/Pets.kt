package com.petstore.demo.controllers

import com.petstore.demo.model.DB
import com.petstore.demo.model.Pet
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException

@RestController
class Pets {
    @PutMapping("/pets")
    fun create(@RequestBody pet: Pet): Int {
        DB.addPet(pet)
        return pet.id
    }

    @GetMapping("/pets/{id}")
    fun get(@PathVariable("id") id: Int) = DB.findPet(id)

    @PostMapping("/pets")
    fun update(@RequestBody updatedPet: Pet) {
        if(updatedPet.id <= 0)
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST)

        DB.updatePet(updatedPet)
    }

    @DeleteMapping("/pets/{id}")
    fun delete(@PathVariable("id") id: Int) = DB.deletePet(id)

    @GetMapping("/pets")
    fun search(@RequestParam(name="name", required=false) name: String?,
               @RequestParam(name="type", required=false) type: String?,
               @RequestParam(name="status", required=false) status: String?) = DB.findPets(name, type, status)
}
