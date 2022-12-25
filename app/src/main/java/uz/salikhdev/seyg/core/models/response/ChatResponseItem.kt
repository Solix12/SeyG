package uz.salikhdev.seyg.core.models.response


import com.google.gson.annotations.SerializedName

data class ChatResponseItem(
    @SerializedName("audio")
    val audio: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("room")
    val room: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("user")
    val user: Int
)