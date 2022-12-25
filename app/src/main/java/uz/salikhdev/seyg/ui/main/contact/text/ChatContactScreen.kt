package uz.salikhdev.seyg.ui.main.contact.text

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.adapter.contact.ContactChatAdapter
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.utils.getAllContact
import uz.salikhdev.seyg.core.utils.gone
import uz.salikhdev.seyg.core.utils.showToast
import uz.salikhdev.seyg.databinding.ScreenContactChatBinding
import uz.salikhdev.seyg.ui.main.ChatViewModel


class ChatContactScreen : BaseFragment(R.layout.screen_contact_chat) {

    private val binding by viewBinding(ScreenContactChatBinding::bind)
    private val adapter by lazy { ContactChatAdapter() }
    private val chatVM : ChatViewModel by viewModels()

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setState()
        observe()

    }

    private fun observe() {
        chatVM.getContact()

        chatVM.contactLD.observe(viewLifecycleOwner) {
            adapter.setData(it!!)
            binding.progressCircular.gone()
        }

        chatVM.errorLD.observe(viewLifecycleOwner){
            showToast(it.toString())
            binding.progressCircular.gone()
        }

        chatVM.networkError.observe(viewLifecycleOwner){
            showToast("Iternet tarmoqi mavjud emas")
            binding.progressCircular.gone()
        }


    }

    private fun setState() {

        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(context)
        adapter.onItemClicked = {

            findNavController().navigate(ChatContactScreenDirections.actionChatContactScreenToChatScreen(it))

        }

    }


}