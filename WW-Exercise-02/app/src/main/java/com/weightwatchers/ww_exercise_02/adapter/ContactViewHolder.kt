package com.weightwatchers.ww_exercise_02.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weightwatchers.ww_exercise_02.contacts.ContactsViewModel
import com.weightwatchers.ww_exercise_02.databinding.LayoutContactBinding
import com.weightwatchers.ww_exercise_02.model.Contact

/**
 * A view holder that represent the contact. 3 fields are:
 * - contactNumberView = the contact's phoneNumber in the list
 * - contactNameView = the contact's name
 * - phoneNumberView = the contact's phone phoneNumber
 */
class ContactViewHolder private constructor(val binding: LayoutContactBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: ContactsViewModel, contact: Contact) {
        binding.viewmodel = viewModel
        binding.contact = contact
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ContactViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LayoutContactBinding.inflate(layoutInflater, parent, false)

            return ContactViewHolder(binding)
        }
    }
}