package com.deepudinesh.tusmindspace.data

import com.google.firebase.auth.FirebaseAuth

object AuthManager {
    private val auth get() = FirebaseAuth.getInstance()

    fun isSignedIn() = auth.currentUser != null
    fun uid(): String? = auth.currentUser?.uid

    fun signInAnonymously(onResult: (success: Boolean, error: String?) -> Unit) {
        auth.signInAnonymously()
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.localizedMessage) }
    }
}