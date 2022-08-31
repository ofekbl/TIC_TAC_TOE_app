package com.example.tictactoe.logic

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface SuggesterApi {
    @Headers(
        "X-RapidAPI-Key: c26ff06453msh9930f7571f03f91p154383jsn028a87338af9",
        "X-RapidAPI-Host: stujo-tic-tac-toe-stujo-v1.p.rapidapi.com"
    )
    @GET("/{game}/{player}")
    suspend fun getBestMove(
        @Path("game") game: String,
        @Path("player") player: String
    ) : Response<com.example.tictactoe.logic.Response>
}

