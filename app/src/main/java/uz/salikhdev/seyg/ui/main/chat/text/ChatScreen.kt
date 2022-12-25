package uz.salikhdev.seyg.ui.main.chat.text

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.adapter.chat.ChatTextAdapter
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.cache.LocalStorage
import uz.salikhdev.seyg.core.models.request.ChatRequest
import uz.salikhdev.seyg.databinding.ScreenChatBinding
import uz.salikhdev.seyg.ui.main.ChatViewModel
import java.util.*


class ChatScreen : BaseFragment(R.layout.screen_chat) {

    private val binding by viewBinding(ScreenChatBinding::bind)
    private val args: ChatScreenArgs by navArgs()
    private val chatVM: ChatViewModel by viewModels()
    private val adapter by lazy { ChatTextAdapter() }
    private val localStorage = LocalStorage()

    override fun onCreated(view: View, savedInstanceState: Bundle?) {


        setState()
        setListener()
        observe()

    }

    private fun setState() {

        binding.recycleView.layoutManager = LinearLayoutManager(context)
        binding.recycleView.adapter = adapter

    }

    private fun observe() {

        callAsynchronousTask()

        chatVM.chatLD.observe(viewLifecycleOwner) {
            adapter.setData(it!!, localStorage.userID)
        }

    }

    fun callAsynchronousTask() {
        val handler = Handler()
        val timer = Timer()
        val doAsynchronousTask: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    try {
                        chatVM.getChat(args.id)
                    } catch (e: Exception) {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        timer.schedule(doAsynchronousTask, 0, 5000) //execute in every 50000 ms
    }

    private fun setListener() {

        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.imageView.setOnClickListener {

            val text = binding.editText.text.toString()

            if (text.isBlank() || text.isEmpty()) {
                return@setOnClickListener
            }
            val body = ChatRequest(null, args.id, text, localStorage.userID)
            chatVM.sendChat(body)
            binding.editText.setText("")

        }
    }


}