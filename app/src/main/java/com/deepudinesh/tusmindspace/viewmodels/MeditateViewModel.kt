package com.deepudinesh.tusmindspace.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepudinesh.tusmindspace.data.MeditationSession
import com.deepudinesh.tusmindspace.data.TUSMindSpaceDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MeditateViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = TUSMindSpaceDatabase.getDatabase(app).meditationSessionDao()

    val history: StateFlow<List<MeditationSession>> = dao.getRecent()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun log(title: String, mins: Int) {
        viewModelScope.launch { dao.insert(MeditationSession(title = title, mins = mins)) }
    }
}