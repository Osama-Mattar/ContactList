package com.weightwatchers.ww_exercise_02.adapter

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.weightwatchers.ww_exercise_02.contacts.ContactsFragmentDirections
import com.weightwatchers.ww_exercise_02.contacts.ContactsViewModel
import com.weightwatchers.ww_exercise_02.model.Contact

class ContactsAdapter(private val viewModel: ContactsViewModel) :
        ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(viewModel, contact)
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${contact.msisdn}")
            }
            it.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            val action = ContactsFragmentDirections.actionContactsFragmentToAddEditContactFragment(contact.msisdn)
            it.findNavController().navigate(action)
            return@setOnLongClickListener true
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.msisdn == newItem.msisdn
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}