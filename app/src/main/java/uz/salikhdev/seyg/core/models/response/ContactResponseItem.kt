package uz.salikhdev.seyg.core.models.response


import com.google.gson.annotations.SerializedName

data class ContactResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: Any
)