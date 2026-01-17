package dam.pmdm.recyclerview_with_retrofit.data.remote

import dam.pmdm.recyclerview_with_retrofit.data.model.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = 1
    ): ItemResponse

}