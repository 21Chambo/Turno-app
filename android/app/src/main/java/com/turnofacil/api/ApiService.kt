package com.turnofacil.api

import com.turnofacil.model.Service
import com.turnofacil.model.TurnRequest
import com.turnofacil.model.TurnResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/servicios")
    fun getServices(): Call<List<Service>>

    @GET("api/servicios/{id}")
    fun getServiceDetail(@Path("id") id: Int): Call<Service>

    @POST("api/turnos")
    fun createTurn(@Body turnRequest: TurnRequest): Call<TurnResponse>
}
