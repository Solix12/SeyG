package uz.salikhdev.seyg.ui.main

import uz.salikhdev.seyg.core.base.BaseViewModel
import uz.salikhdev.seyg.core.models.request.ChatRequest
import uz.salikhdev.seyg.core.models.response.ChatResponse
import uz.salikhdev.seyg.core.models.response.ContactResponse
import uz.salikhdev.seyg.core.repo.ChatRepository
import uz.salikhdev.seyg.core.utils.ResultWrapper
import uz.salikhdev.seyg.core.utils.SingleLivewEvents

class ChatViewModel : BaseViewModel() {

    private val repository = ChatRepository()

    val contactLD = SingleLivewEvents<ContactResponse?>()
    val chatLD = SingleLivewEvents<ChatResponse?>()
    val sendChatLD = SingleLivewEvents<String?>()


    val errorLD = SingleLivewEvents<Int?>()
    val networkError = SingleLivewEvents<String?>()


    fun getContact() {

        launch {

            when (val response = repository.getContact()) {
                is ResultWrapper.Success -> {
                    contactLD.postValue(response.response)
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

    fun getChat(id: Int) {

        launch {

            when (val response = repository.getChat(id)) {
                is ResultWrapper.Success -> {
                    chatLD.postValue(response.response)
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


    fun sendChat(body: ChatRequest) {

        launch {

            when (val response = repository.sendChat(body)) {
                is ResultWrapper.Success -> {
                    sendChatLD.postValue(response.response)
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

    fun sendVoiceChat(body: ChatRequest) {

        launch {
            repository.sendVoiceChat(body)
        }

    }


}