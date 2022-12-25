package uz.salikhdev.seyg.core.models.request


import com.google.gson.annotations.SerializedName
import java.io.File

data class ChatRequest(
    @SerializedName("audio")
    val audio: File?,
    @SerializedName("room")
    val room: Any,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: Any
)