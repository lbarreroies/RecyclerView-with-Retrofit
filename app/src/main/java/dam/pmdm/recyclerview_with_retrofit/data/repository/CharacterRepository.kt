package dam.pmdm.recyclerview_with_retrofit.data.repository

import dam.pmdm.recyclerview_with_retrofit.data.model.Item
import dam.pmdm.recyclerview_with_retrofit.data.remote.RetrofitInstance

class CharacterRepository {
    suspend fun getCharacters(): List<Item> {
        val charactersList = mutableListOf<Item>()

        for (page in 1..42) {
            val response = RetrofitInstance.api.getCharacters(page)
            charactersList.addAll(response.results)
        }

        return charactersList
    }
}