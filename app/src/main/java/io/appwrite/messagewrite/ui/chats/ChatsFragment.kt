package io.appwrite.messagewrite.ui.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.messagewrite.R
import io.appwrite.messagewrite.databinding.FragmentChatsBinding

@AndroidEntryPoint
class ChatsFragment : Fragment(), MenuProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .findViewById<ChipGroup>(R.id.nav_view)
            .visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vm = ViewModelProvider(this).get(ChatsViewModel::class.java)

        val binding = FragmentChatsBinding.inflate(inflater, container, false)

        val adapter = ChatAdapter { chat, _ ->
            findNavController().navigate(
                ChatsFragmentDirections.actionChatsToChat(chat.id)
            )
        }

        binding.recyclerChats.adapter = adapter

        vm.chats.observe(viewLifecycleOwner) {
            adapter.setChats(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.chats, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}