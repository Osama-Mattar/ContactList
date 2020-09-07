package com.weightwatchers.ww_exercise_02.addeditcontact

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.weightwatchers.ww_exercise_02.EventObserver
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.base.BaseFragment
import com.weightwatchers.ww_exercise_02.databinding.AddEditContactFragmentBinding
import com.weightwatchers.ww_exercise_02.utils.setupSnackbar

class AddEditContactFragment : BaseFragment() {

    private val args: AddEditContactFragmentArgs by navArgs()

    private lateinit var viewDataBinding: AddEditContactFragmentBinding

    private val viewModel by viewModels<AddEditContactViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.add_edit_contact_fragment, container, false)
        viewDataBinding = AddEditContactFragmentBinding.bind(root).apply {
            this.viewmodel = viewModel
        }
        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewModel.start(args.msisdn)
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSnackbar()
        setupNavigation()
    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)
    }

    private fun setupNavigation() {
        viewModel.deleteContactEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_addEditContactFragment_to_contactsFragment)
        })
        viewModel.contactUpdatedEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_addEditContactFragment_to_contactsFragment)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                viewModel.deleteContact()
                true
            }
            else -> false
        }
    }
}