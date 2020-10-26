package com.mattar.contactlist.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mattar.contactlist.*
import com.mattar.contactlist.R
import com.mattar.contactlist.model.Contact
import com.mattar.contactlist.model.Result
import com.mattar.contactlist.model.Result.Success
import com.mattar.contactlist.model.source.ContactsRepository
import kotlinx.coroutines.launch

class ContactsViewModel @ViewModelInject constructor(
        contactsRepository: ContactsRepository
) : ViewModel() {

    private val _items: LiveData<List<Contact>> = contactsRepository.observeContact()
            .distinctUntilChanged()
            .switchMap { loadContacts(it) }

    val items: LiveData<List<Contact>> = _items

    private var resultMessageShown: Boolean = false

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private fun loadContacts(contactsResult: Result<List<Contact>>): LiveData<List<Contact>> {
        val result = MutableLiveData<List<Contact>>()

        if (contactsResult is Success) {
            viewModelScope.launch {
                result.value = contactsResult.data
            }
        } else {
            result.value = emptyList()
            showSnackbarMessage(R.string.loading_contacts_error)
        }

        return result
    }

    fun showEditResultMessage(result: Int) {
        if (resultMessageShown) return
        when (result) {
            EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_saved_contact_message)
            ADD_EDIT_RESULT_OK -> showSnackbarMessage(R.string.successfully_added_contact_message)
            DELETE_RESULT_OK -> showSnackbarMessage(R.string.successfully_deleted_contact_message)
        }
        resultMessageShown = true
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}