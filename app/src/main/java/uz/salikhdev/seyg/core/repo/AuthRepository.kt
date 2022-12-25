package uz.salikhdev.seyg.core.repo

import kotlinx.coroutines.Dispatchers
import uz.salikhdev.seyg.core.models.request.LoginRequest
import uz.salikhdev.seyg.core.models.request.RegisterRequest
import uz.salikhdev.seyg.core.models.response.LoginResponse
import uz.salikhdev.seyg.core.models.response.RegisterResponse
import uz.salikhdev.seyg.core.network.ApiClient
import uz.salikhdev.seyg.core.utils.ResultWrapper
import uz.salikhdev.seyg.core.utils.parseResponse

class AuthRepository {

    private val service = ApiClient.getAuthService()


    suspend fun sendRegister(body: RegisterRequest): ResultWrapper<RegisterResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.sendRegister(body)
        }
    }


    suspend fun sendLogin(body: LoginRequest): ResultWrapper<LoginResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.sendLogin(body)
        }
    }

}