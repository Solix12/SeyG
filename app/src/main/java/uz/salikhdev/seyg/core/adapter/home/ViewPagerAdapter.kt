package uz.salikhdev.seyg.core.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import uz.salikhdev.seyg.core.models.ViewPagerModel
import uz.salikhdev.seyg.databinding.ViewPagerBinding

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private val data = ArrayList<ViewPagerModel>()
    var onItemClicked: ((id: Int) -> Unit)? = null
    var onItemScroll: ((id: String) -> Unit)? = null
    private var viewPager2: ViewPager2? = null

    fun setViewPager(viewPager2: ViewPager2) {
        this.viewPager2 = viewPager2
    }

    fun setData(data: ArrayList<ViewPagerModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ViewPagerModel) {

            binding.imageView.setImageResource(data.image)
            binding.root.contentDescription = data.description

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewPagerBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(data[position].id)
        }

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onItemScroll?.invoke(data[position].description)
            }
        })

    }

    override fun getItemCount(): Int = data.size
}
