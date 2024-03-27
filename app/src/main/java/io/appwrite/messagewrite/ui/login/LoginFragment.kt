package io.appwrite.messagewrite.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.messagewrite.R
import io.appwrite.messagewrite.databinding.FragmentLoginBinding
import io.appwrite.messagewrite.ui.register.RegisterViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .findViewById<ChipGroup>(R.id.nav_view)
            .visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(this).get(LoginViewModel::class.java)

        vm.error.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        }

        vm.user.observe(viewLifecycleOwner) {
            findNavController().navigate(LoginFragmentDirections.actionLoginToChats())
        }

        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToRegister())
        }

        binding.btnLogin.setOnClickListener {
            vm.login(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        return binding.root
    }
}