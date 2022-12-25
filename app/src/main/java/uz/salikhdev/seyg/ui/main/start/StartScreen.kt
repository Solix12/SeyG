package uz.salikhdev.seyg.ui.main.start

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.cache.LocalStorage


class StartScreen : BaseFragment(R.layout.screen_start) {

    private val cache = LocalStorage()

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        if (cache.isFirst) {
            findNavController().navigate(StartScreenDirections.actionStartScreenToRegisterScreen())
        } else {
            findNavController().navigate(StartScreenDirections.actionStartScreenToHomeScreen())
        }

    }

}