package uz.salikhdev.seyg.ui.main.home

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.adapter.home.ViewPagerAdapter
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.models.ViewPagerModel
import uz.salikhdev.seyg.databinding.ScreenHomeBinding
import java.util.*


class HomeScreen : BaseFragment(R.layout.screen_home) {

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val adapter by lazy { ViewPagerAdapter() }
    private var spech: TextToSpeech? = null

    override fun onCreated(view: View, savedInstanceState: Bundle?) {
        setListener()
        setState()

    }

    private fun setListener() {

        spech = TextToSpeech(requireContext()) { status ->

            if (status == TextToSpeech.SUCCESS) {
                spech?.language = Locale.ENGLISH
                speach("Ovoz")
            }

        }
        adapter.setViewPager(binding.viewPager)
        adapter.onItemScroll = {

            speach(it)

        }

    }

    private fun setState() {

        binding.viewPager.adapter = adapter


        val data = ArrayList<ViewPagerModel>()

        data.add(ViewPagerModel(1, R.drawable.voice, "Ovoz"))
        data.add(ViewPagerModel(2, R.drawable.chat, "Text"))

        adapter.setData(data)



        adapter.onItemClicked = { id ->

            when (id) {
                1 -> {
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToVoiceScreen())
                }
                2 -> {
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToChatScreen())
                }
            }

        }

    }

    private fun speach(text: String) {
        spech?.speak(text, TextToSpeech.QUEUE_FLUSH, null)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (spech != null) {
            spech?.stop()
            spech?.shutdown()
        }
    }


}