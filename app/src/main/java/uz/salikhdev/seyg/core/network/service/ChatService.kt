package uz.salikhdev.seyg.core.network.service

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import uz.salikhdev.seyg.core.models.request.ChatRequest
import uz.salikhdev.seyg.core.models.response.ChatResponse
import uz.salikhdev.seyg.core.models.response.ContactResponse

interface ChatService {


    @GET("/room/")
    suspend fun getContact(): Response<ContactResponse?>

    @GET("/messages-by-room/{id}/")
    suspend fun getChat(@Path("id") id: Int): Response<ChatResponse?>


    @POST("/message/")
    suspend fun sendChat(@Body body: ChatRequest): Response<String?>

    @Multipart
    @POST("/message/")
    suspend fun sendChat(
        @Part
        audio: MultipartBody.Part,
        @Part
        text: MultipartBody.Part,
        @Part
        room: MultipartBody.Part,
        @Part
        user: MultipartBody.Part,
        )
}