package uz.salikhdev.seyg.core.repo

import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.salikhdev.seyg.core.models.request.ChatRequest
import uz.salikhdev.seyg.core.models.response.ChatResponse
import uz.salikhdev.seyg.core.models.response.ContactResponse
import uz.salikhdev.seyg.core.network.ApiClient
import uz.salikhdev.seyg.core.utils.ResultWrapper
import uz.salikhdev.seyg.core.utils.parseResponse


class ChatRepository {

    private val service = ApiClient.getChatService()


    suspend fun getContact(): ResultWrapper<ContactResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.getContact()
        }
    }

    suspend fun getChat(id: Int): ResultWrapper<ChatResponse?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.getChat(id)
        }
    }

    suspend fun sendChat(body: ChatRequest): ResultWrapper<String?, Any?> {
        return parseResponse(Dispatchers.IO) {
            service.sendChat(body)
        }
    }

    suspend fun sendVoiceChat(body: ChatRequest){


        val requestBody: RequestBody = RequestBody.Companion.create("audio/*".toMediaTypeOrNull(), body.audio!!)
        val audioPart = MultipartBody.Part.createFormData("AudioComment", body.audio.name, requestBody);
        val text = MultipartBody.Part.createFormData("text", body.text)
        val room = MultipartBody.Part.createFormData("room", body.room.toString())
        val user = MultipartBody.Part.createFormData("user", body.user.toString())
        service.sendChat(audioPart, text, room, user)

    }

}