package com.weightwatchers.ww_exercise_02.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.adapter.ContactsAdapter
import com.weightwatchers.ww_exercise_02.base.BaseFragment
import com.weightwatchers.ww_exercise_02.databinding.ContactsFragmentBinding
import com.weightwatchers.ww_exercise_02.utils.setupSnackbar

class ContactsFragment : BaseFragment() {

    private val viewModel by viewModels<ContactsViewModel>()

    private val args: ContactsFragmentArgs by navArgs()

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
        setupSnackbar()
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
        val action = ContactsFragmentDirections.actionContactsFragmentToAddEditContactFragment(null, resources.getString(R.string.add_contact))
        findNavController().navigate(action)
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
        arguments?.let {
            viewModel.showEditResultMessage(args.userMessage)
        }
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