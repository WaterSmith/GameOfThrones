package ru.skillbranch.gameofthrones.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface RetrofitApi {
    //https://www.anapioficeandfire.com/api/houses/?page=1&pageSize=50
    @GET("houses")
    suspend fun getHousesByPage(@Query("page") page:Int = 1, @Query("pageSize") pageSize:Int = 50 ): Response<List<HouseRes>>

    @GET("houses")
    suspend fun getHouseByName(@Query("name") name:String):Response<List<HouseRes>>

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id:String):Response<CharacterRes>
}