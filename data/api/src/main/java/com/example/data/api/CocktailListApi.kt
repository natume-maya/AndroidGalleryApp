package com.example.data.api

import com.example.data.api.result.CocktailResult
import retrofit2.http.GET

interface CocktailApi {
    @GET("cocktails")
    suspend fun cocktailsAll(): CocktailResult
}