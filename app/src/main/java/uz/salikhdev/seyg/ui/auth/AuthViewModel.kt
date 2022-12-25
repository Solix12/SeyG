package uz.salikhdev.seyg.ui.auth

import uz.salikhdev.seyg.core.base.BaseViewModel
import uz.salikhdev.seyg.core.models.request.LoginRequest
import uz.salikhdev.seyg.core.models.request.RegisterRequest
import uz.salikhdev.seyg.core.models.response.LoginResponse
import uz.salikhdev.seyg.core.repo.AuthRepository
import uz.salikhdev.seyg.core.utils.ResultWrapper
import uz.salikhdev.seyg.core.utils.SingleLivewEvents

class AuthViewModel : BaseViewModel() {

    private val repository = AuthRepository()

    val registerLD = SingleLivewEvents<Any?>()
    val loginLD = SingleLivewEvents<LoginResponse?>()

    val errorLD = SingleLivewEvents<Int?>()
    val networkError = SingleLivewEvents<String?>()


    fun sendRegister(body: RegisterRequest) {

        launch {

            when (val response = repository.sendRegister(body)) {
                is ResultWrapper.Success -> {
                    registerLD.postValue(response.response)
                }
                is ResultWrapper.ErrorResponse -> {
                    errorLD.postValue(response.code)
                }
                is ResultWrapper.NetworkError -> {
                    networkError.postValue("NT")
                }
            }

        }

    }

    fun sendLogin(body: LoginRequest) {

        launch {

            when (val response = repository.sendLogin(body)) {
                is ResultWrapper.Success -> {
                    loginLD.postValue(response.response)
                }
                is ResultWrapper.ErrorResponse -> {
                    errorLD.postValue(response.code)
                }
                is ResultWrapper.NetworkError -> {
                    networkError.postValue("NT")
                }
            }

        }

    }


}