package uz.salikhdev.seyg.ui.main.contact.voice

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.adapter.contact.ContactVoiceAdapter
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.utils.gone
import uz.salikhdev.seyg.core.utils.showToast
import uz.salikhdev.seyg.databinding.ScreenContactVoiceBinding
import uz.salikhdev.seyg.ui.main.ChatViewModel
import java.util.*


class VoiceContactScreen : BaseFragment(R.layout.screen_contact_voice) {

    private val binding by viewBinding(ScreenContactVoiceBinding::bind)
    private val adapter by lazy { ContactVoiceAdapter() }
    private var spech: TextToSpeech? = null
    private val chatVM: ChatViewModel by viewModels()


    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setState()
        setListener()
        observe()


    }

    private fun observe() {
        chatVM.getContact()

        chatVM.contactLD.observe(viewLifecycleOwner) {
            speach(it!![0].name)
            adapter.setData(it)
            binding.progressCircular.gone()
        }

        chatVM.errorLD.observe(viewLifecycleOwner){
            showToast(it.toString())
            binding.progressCircular.gone()
        }

        chatVM.networkError.observe(viewLifecycleOwner){
            showToast("Iternet tarmoqi mavjud emas")
            speach("Iternet tarmoqi mavjud emas")
            binding.progressCircular.gone()
        }


    }

    private fun setListener() {

        spech = TextToSpeech(requireContext()) { status ->

            if (status == TextToSpeech.SUCCESS) {
                spech?.language = Locale.ENGLISH
            }

        }
        adapter.setViewPager(binding.viewPager)
        adapter.onItemScroll = {

            speach(it)

        }


    }

    private fun setState() {

        binding.viewPager.adapter = adapter
        adapter.onItemClicked = {


            findNavController().navigate(VoiceContactScreenDirections.actionVoiceContactScreenToVoiceScreen(
                it.name , it.id))

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