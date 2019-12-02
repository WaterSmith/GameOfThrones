package ru.skillbranch.gameofthrones.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbranch.gameofthrones.data.remote.res.HouseResponse

interface RetrofitApi {
    //https://www.anapioficeandfire.com/api/houses/?page=1&pageSize=50
    @GET("/houses")
    fun getHousesByPage(@Query("page") page:Int, @Query("pageSize") pageSize:Int ): Deferred<Response<HouseResponse>>
}