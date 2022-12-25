package uz.salikhdev.seyg.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.models.request.RegisterRequest
import uz.salikhdev.seyg.core.utils.gone
import uz.salikhdev.seyg.core.utils.showToast
import uz.salikhdev.seyg.core.utils.visible
import uz.salikhdev.seyg.databinding.ScreenRegisterBinding
import uz.salikhdev.seyg.ui.auth.AuthViewModel


class RegisterScreen : BaseFragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val authVM: AuthViewModel by viewModels()


    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setListener()
        observe()


    }

    private fun observe() {
        authVM.registerLD.observe(viewLifecycleOwner) {
            findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToLoginScreen())
            binding.progress.gone()
        }
        authVM.errorLD.observe(viewLifecycleOwner) {
            showToast(it.toString())
            binding.progress.gone()
        }
        authVM.networkError.observe(viewLifecycleOwner) {
            showToast("Internet tarmoqi mavjud emas")
            binding.progress.gone()
        }
    }

    private fun setListener() {

        binding.login.setOnClickListener {
            findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToLoginScreen())
        }


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

            val body = RegisterRequest(password, password, name)
            binding.progress.visible()
            authVM.sendRegister(body)

        }


    }

}