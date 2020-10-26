package com.mattar.contactlist.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mattar.contactlist.model.Contact

/**
 * [BindingAdapter]s for the [Contact]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Contact>?) {
    items?.let {
        (listView.adapter as ContactsAdapter).submitList(items)
    }
}