package com.weightwatchers.ww_exercise_02.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.adapter.ContactsAdapter
import com.weightwatchers.ww_exercise_02.base.BaseFragment
import com.weightwatchers.ww_exercise_02.databinding.ContactsFragmentBinding

class ContactsFragment : BaseFragment() {

    private val viewModel by viewModels<ContactsViewModel>()

    private lateinit var viewDataBinding: ContactsFragmentBinding
    private lateinit var listAdapter: ContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = ContactsFragmentBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupFab()
    }


    private fun setupFab() {
        activity?.findViewById<FloatingActionButton>(R.id.add_contact_fab)?.let {
            it.setOnClickListener {
                navigateToAddNewContact()
            }
        }
    }

    private fun navigateToAddNewContact() {
        findNavController().navigate(R.id.action_contactsFragment_to_addEditContactFragment)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = ContactsAdapter(viewModel)
            viewDataBinding.contactsList.apply {
                adapter = listAdapter
                addItemDecoration(
                        DividerItemDecoration(activity,
                                VERTICAL)
                )
            }
        } else {
            Log.w("", "ViewModel not initialized when attempting to set up adapter.")
        }
    }
}