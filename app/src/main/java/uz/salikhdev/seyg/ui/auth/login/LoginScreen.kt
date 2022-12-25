package uz.salikhdev.seyg.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.cache.LocalStorage
import uz.salikhdev.seyg.core.models.request.LoginRequest
import uz.salikhdev.seyg.core.utils.gone
import uz.salikhdev.seyg.core.utils.showToast
import uz.salikhdev.seyg.core.utils.visible
import uz.salikhdev.seyg.databinding.ScreenLoginBinding
import uz.salikhdev.seyg.ui.auth.AuthViewModel


class LoginScreen : BaseFragment(R.layout.screen_login) {

    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val authVM: AuthViewModel by viewModels()
    private val local = LocalStorage()

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setListener()
        observe()

    }

    private fun observe() {
        authVM.loginLD.observe(viewLifecycleOwner) {
            local.token = it!!.token
            local.isFirst = false
            local.userID = it.user.id
            local.userID = it.user.id
            local.userName = it.user.username
            findNavController().navigate(LoginScreenDirections.actionLoginScreenToHomeScreen())
            binding.progress.gone()
        }
        authVM.errorLD.observe(viewLifecycleOwner) {
            showToast("Isim yoki Parol notog'ri")
            binding.progress.gone()

        }
        authVM.networkError.observe(viewLifecycleOwner) {
            showToast("Internet tarmoqi mavjud emas")
            binding.progress.gone()

        }
    }

    private fun setListener() {

        binding.nextBtn.setOnClickListener {

            val name = binding.userEdit.text.toString()
            val password = binding.password.text.toString()

            if (name.isEmpty() || name.isBlank()) {
                binding.userEdit.error = "Ismingizni kiriting"
                return@setOnClickListener
            }
            if (password.isEmpty() || password.isBlank()) {
                binding.userEdit.error = "Parolni kiriting"
                return@setOnClickListener
            }

            val body = LoginRequest(password, name)
            binding.progress.visible()
            authVM.sendLogin(body)

        }

    }

}