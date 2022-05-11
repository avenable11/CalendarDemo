package edu.ivytech.calendardemo.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

object FirestoreUtil {

    fun getAllUsers() : Task<QuerySnapshot> {
        return FirebaseFirestore.getInstance()
            .collection("users").get()
    }
}