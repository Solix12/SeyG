package uz.salikhdev.seyg.core.adapter.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.salikhdev.seyg.core.models.ContactModel
import uz.salikhdev.seyg.core.models.response.ContactResponseItem
import uz.salikhdev.seyg.databinding.ItemContactBinding

class ContactChatAdapter : RecyclerView.Adapter<ContactChatAdapter.ViewHolder>() {

    private val data = ArrayList<ContactResponseItem>()
    var onItemClicked: ((id: Int) -> Unit)? = null

    fun setData(data: ArrayList<ContactResponseItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ContactResponseItem) {

            binding.textView3.text = data.name


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(data[position].id)
        }
    }

    override fun getItemCount(): Int = data.size
}
