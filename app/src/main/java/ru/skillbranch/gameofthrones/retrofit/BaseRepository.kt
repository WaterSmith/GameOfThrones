package ru.skillbranch.gameofthrones.retrofit

import android.util.Log
import retrofit2.Response
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import java.io.IOException

object BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result : Result<T> = safeApiResult(call,errorMessage)
        var data : T? = null

        when(result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }


        return data

    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T>, errorMessage: String) : Result<T>{
        val response = call.invoke()
        if(response.isSuccessful) return Result.Success(response.body()!!)

        return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
    }
}

/*
class HousesRepository(private val api : RetrofitApi) : BaseRepository() {

    fun getHousesByPage(pageNumber:Int) : MutableList<HouseRes>?{

        val houseResponse = safeApiCall(
            call = {api.getHousesByPage(pageNumber, 50).await()},
            errorMessage = "Error Fetching Popular Movies"
        )

        return houseResponse?.result?.toMutableList()

    }

}
*/
