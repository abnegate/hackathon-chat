package io.appwrite.messagewrite.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.messagewrite.R
import io.appwrite.messagewrite.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(this).get(SplashViewModel::class.java)

        vm.user.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(SplashFragmentDirections.actionSplashToChats())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashToLogin())
            }
        }

        vm.getUser()

        val binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root
    }
}