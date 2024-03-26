package io.appwrite.messagewrite.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.messagewrite.databinding.FragmentContactsBinding

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(this).get(ContactsViewModel::class.java)

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}