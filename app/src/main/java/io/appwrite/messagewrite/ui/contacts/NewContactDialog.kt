package io.appwrite.messagewrite.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.messagewrite.databinding.DialogContactNewBinding

@AndroidEntryPoint
class NewContactDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(this).get(NewContactViewModel::class.java)

        vm.error.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).apply {
                view.elevation = 100f
            }.show()
        }

        vm.contact.observe(viewLifecycleOwner) {
            dismiss()
        }

        val binding = DialogContactNewBinding.inflate(inflater, container, false)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAdd.setOnClickListener {
            vm.createContact(
                binding.emailEditText.text.toString(),
            )
        }

        return binding.root
    }
}