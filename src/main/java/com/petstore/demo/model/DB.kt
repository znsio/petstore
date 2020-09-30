package com.petstore.demo.model

object DB {
    private val pets: MutableList<Pet> = mutableListOf(Pet("Archibald", "dog", "available", 10), Pet("Gemini", "dog", "available", 20))
    private val orders: MutableList<Order> = mutableListOf(Order("dog", 2, "pending", 10), Order("cat", 1, "pending", 20))

    fun addPet(pet: Pet) { pets.add(pet) }

    fun findPet(id: Int) = pets.first { it.id == id }

    fun updatePet(update: Pet) {
        val index = pets.indexOfFirst { pet -> pet.id == update.id }
        pets[index].newPet(update.name, update.type, update.status).let {
            pets.set(index, it)
        }
    }

    fun deletePet(id: Int) { pets.removeIf { it.id == id } }

    fun findPets(name: String?, type: String?, status: String?) = pets.filter { it.name == name || it.type == type || it.status == status }

    fun addOrder(order: Order) { orders.add(order) }

    fun reservePetsFor(order: Order): Boolean {
        val petsFound = pets.filter { pet -> pet.type == order.type }.take(order.count)

        return (petsFound.size == order.count).also { found ->
            if(found) {
                for (pet in petsFound) {
                    pet.status = "sold"
                }
            }
        }
    }

    fun getOrder(id: Int) = orders.first { it.id == id }

    fun deleteOrder(id: Int) {
        orders.removeIf { it.id == id }
    }

    fun updateOrderStatus(id: Int, status: String) {
        orders.first { it.id == id }.status = status
    }

    fun findOrders(status: String?, type: String?) = orders.filter { it.status == status || it.type == type }

    fun cleanSlate() {
        pets.clear()
        orders.clear()
    }
}
