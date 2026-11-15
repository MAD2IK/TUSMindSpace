package com.deepudinesh.tusmindspace.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepudinesh.tusmindspace.data.Quote
import com.deepudinesh.tusmindspace.data.TUSMindSpaceDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val defaultQuotes = listOf(
    Quote(text = "You don't have to see the whole staircase, just take the first step.", author = "Martin Luther King Jr."),
    Quote(text = "Almost everything will work again if you unplug it for a few minutes, including you.", author = "Anne Lamott"),
    Quote(text = "Breathe. You're going to be okay.", author = "Unknown"),
    Quote(text = "Rest is not idleness.", author = "John Lubbock"),
    Quote(text = "What you think, you become.", author = "Buddha"),
    Quote(text = "Be gentle with yourself. You're doing the best you can.", author = "Unknown"),
    Quote(text = "Feelings are just visitors. Let them come and go.", author = "Mooji")
)

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = TUSMindSpaceDatabase.getDatabase(app).quoteDao()

    val quotes: StateFlow<List<Quote>> = dao.getAllQuotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            if (dao.count() == 0) dao.insertAll(defaultQuotes)
        }
    }
}