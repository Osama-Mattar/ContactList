package com.weightwatchers.ww_exercise_02.contacts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.weightwatchers.ww_exercise_02.Event
import com.weightwatchers.ww_exercise_02.R
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.model.Result
import com.weightwatchers.ww_exercise_02.model.Result.Success
import com.weightwatchers.ww_exercise_02.model.source.ContactsRepository
import kotlinx.coroutines.launch

class ContactsViewModel @ViewModelInject constructor(
        contactsRepository: ContactsRepository
) : ViewModel() {

    private val _items: LiveData<List<Contact>> = contactsRepository.observeContact()
            .distinctUntilChanged()
            .switchMap { loadContacts(it) }

    val items: LiveData<List<Contact>> = _items

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }
    private val _snackbarText = MutableLiveData<Event<Int>>()

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

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }
}