package uz.texnopos.firestoredatatorecyclerview

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()
    fun eventChangeListener(
        onUserAdded: (user: User) -> Unit, onUserUpdated: (user: User) -> Unit,
        onUserDeleted: (user: User) -> Unit, onFailure: (msg: String?) -> Unit
    ) {
        db.collection("Users").orderBy("firstName", Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    onFailure.invoke(error.localizedMessage)
                    Log.e("Firestore error !", error.localizedMessage!!)
                } else {
                    for (dc in value!!.documentChanges) {
                        val doc = dc.document.toObject(User::class.java)
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                onUserAdded.invoke(doc)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                onUserUpdated.invoke(doc)
                            }
                            DocumentChange.Type.REMOVED -> {
                                onUserDeleted.invoke(doc)
                            }
                        }
                    }
                }
            }
    }
}