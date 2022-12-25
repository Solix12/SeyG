package uz.salikhdev.seyg.core.adapter.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import uz.salikhdev.seyg.core.models.response.ContactResponseItem
import uz.salikhdev.seyg.databinding.ItemContactVoiceBinding

class ContactVoiceAdapter : RecyclerView.Adapter<ContactVoiceAdapter.ViewHolder>() {

    private val data = ArrayList<ContactResponseItem>()
    var onItemClicked: ((id: ContactResponseItem) -> Unit)? = null
    var onItemScroll: ((id: String) -> Unit)? = null
    private var viewPager2: ViewPager2? = null

    fun setViewPager(viewPager2: ViewPager2) {
        this.viewPager2 = viewPager2
    }

    fun setData(data: ArrayList<ContactResponseItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemContactVoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ContactResponseItem) {

            binding.textView3.text = data.name


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContactVoiceBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(data[position])
        }

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onItemScroll?.invoke(data[position].name)
            }
        })
    }

    override fun getItemCount(): Int = data.size
}
