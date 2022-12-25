package uz.salikhdev.seyg.core.network.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.salikhdev.seyg.core.models.request.LoginRequest
import uz.salikhdev.seyg.core.models.request.RegisterRequest
import uz.salikhdev.seyg.core.models.response.LoginResponse
import uz.salikhdev.seyg.core.models.response.RegisterResponse

interface AuthService {


    @POST("/client/signup/")
    suspend fun sendRegister(@Body body: RegisterRequest): Response<RegisterResponse?>

    @POST("/client/log/")
    suspend fun sendLogin(@Body body: LoginRequest): Response<LoginResponse?>

}