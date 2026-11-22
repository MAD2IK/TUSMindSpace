package com.deepudinesh.tusmindspace.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.deepudinesh.tusmindspace.data.CampusEvent
import com.deepudinesh.tusmindspace.data.TUSMindSpaceDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

private fun at(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long =
    Calendar.getInstance().apply {
        clear()
        set(year, month, day, hour, minute)
    }.timeInMillis

private val defaultEvents = listOf(
    CampusEvent(title = "Mindfulness Drop-in", startMillis = at(2026, Calendar.NOVEMBER, 12, 13, 0), location = "Library Room 2", lat = 52.6747, lon = -8.6493),
    CampusEvent(title = "Coffee & Chat", startMillis = at(2026, Calendar.NOVEMBER, 14, 11, 0), location = "Student Centre", lat = 52.6743, lon = -8.6485),
    CampusEvent(title = "Exam Stress Workshop", startMillis = at(2026, Calendar.NOVEMBER, 19, 16, 0), location = "Lecture Hall B", lat = 52.6750, lon = -8.6488),
    CampusEvent(title = "Movie Night", startMillis = at(2026, Calendar.NOVEMBER, 21, 19, 0), location = "SU Common Room", lat = 52.6741, lon = -8.6491),
    CampusEvent(title = "Walk & Talk", startMillis = at(2026, Calendar.NOVEMBER, 27, 12, 30), location = "Main Entrance", lat = 52.6738, lon = -8.6497)
)

class ResourcesViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = TUSMindSpaceDatabase.getDatabase(app).campusEventDao()

    val events: StateFlow<List<CampusEvent>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch {
            if (dao.count() == 0) dao.insertAll(defaultEvents)
        }
    }
}