package com.weightwatchers.ww_exercise_02.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weightwatchers.ww_exercise_02.model.Contact

/**
 * [BindingAdapter]s for the [Contact]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Contact>?) {
    items?.let {
        (listView.adapter as ContactsAdapter).submitList(items)
    }
}