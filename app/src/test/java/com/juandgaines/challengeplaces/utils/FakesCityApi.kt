package com.juandgaines.challengeplaces.utils

import com.juandgaines.challengeplaces.data.network.CitiesApi
import com.juandgaines.challengeplaces.data.network.CitiesDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeCitiesApi: CitiesApi {
    var shouldFail: Boolean = false
    private val errorBody = """
                {
                  "success": false,
                  "status_code": 500,
                  "status_message": "Internal Server Error"
                }
            """.trimIndent().toResponseBody(
        "application/json".toMediaTypeOrNull()
    )
    override suspend fun getCities(): Response<List<CitiesDto>> {
        return if (shouldFail) {
            Response.error(500,errorBody )
        } else {
            Response.success(200, providerCitiesDto)
        }
    }
}
