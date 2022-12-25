package uz.salikhdev.seyg.core.adapter.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.salikhdev.seyg.core.models.response.ChatResponseItem
import uz.salikhdev.seyg.databinding.ItemMessageReceivedBinding
import uz.salikhdev.seyg.databinding.ItemMessageSentBinding

class ChatTextAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data = ArrayList<ChatResponseItem>()

    private val TYPE_MESSAGE_SENT = 0
    private val TYPE_MESSAGE_RECEIVED = 1
    private var user = 0

    fun setData(data: ArrayList<ChatResponseItem>, user: Int) {
        this.data.clear()
        this.user = user
        this.data.addAll(data)
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return if (data[position].user == user) {
            TYPE_MESSAGE_SENT
        } else {
            TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            TYPE_MESSAGE_SENT -> {
                return ViewHolderSender(
                    ItemMessageSentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ViewHolderReceiver(
                    ItemMessageReceivedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = data[position]

        if (currentMessage.user == user) {
            val h = holder as ViewHolderSender
            h.binding.sentTxt.text = currentMessage.text
        } else {
            val h = holder as ViewHolderReceiver
            h.binding.receivedTxt.text = currentMessage.text
        }


    }

    override fun getItemCount(): Int = data.size


    class ViewHolderSender(val binding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolderReceiver(val binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}
