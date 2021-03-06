package team.devloopy.kiu_exchange_rate.model

import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap
import retrofit2.http.Url

@JvmSuppressWildcards
interface BasicApi {
    @GET
    fun get(
        @Url url: String,
        @QueryMap params: Map<String, Any?> = mapOf(),
        @HeaderMap headers: Map<String, Any?> = mapOf()
    ): Flowable<JsonObject>
}