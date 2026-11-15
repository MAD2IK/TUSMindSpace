package com.deepudinesh.tusmindspace.viewmodels

import android.app.Application
import android.text.format.DateFormat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepudinesh.tusmindspace.data.CheckIn
import com.deepudinesh.tusmindspace.data.TUSMindSpaceDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.TimeZone

private fun dayNumber(ms: Long) = (ms + TimeZone.getDefault().getOffset(ms)) / 86_400_000L
class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val db = TUSMindSpaceDatabase.getDatabase(app)
    private val checkInDao = db.checkInDao()

    val checkIns: StateFlow<List<CheckIn>> = checkInDao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val streak: StateFlow<Int> = checkInDao.count()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val week: StateFlow<List<Pair<String, Int>>> = checkInDao.getAll().map { list ->
        val now = System.currentTimeMillis()
        val today = dayNumber(now)
        (6 downTo 0).map { back ->
            val label = DateFormat.format("EEE", now - back * 86_400_000L).toString()
            label to (list.firstOrNull { dayNumber(it.createdAt) == today - back }?.mood ?: 0)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun logCheckIn(mood: Int) {
        viewModelScope.launch { checkInDao.insert(CheckIn(mood = mood)) }
    }
}