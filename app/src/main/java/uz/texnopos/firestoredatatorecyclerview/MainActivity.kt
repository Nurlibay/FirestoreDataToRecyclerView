package uz.texnopos.firestoredatatorecyclerview

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import uz.texnopos.firestoredatatorecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivityMainBinding
    private val myAdapter: MyAdapter = MyAdapter()
    private lateinit var db: FirebaseFirestore
    private lateinit var models: MutableList<User>
    private lateinit var progressDialog: ProgressDialog
    private val dbHelper = FirebaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.recyclerView.adapter = myAdapter

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Fetching data ... ")
        progressDialog.show()
        dbHelper.eventChangeListener(
            {
                myAdapter.add(it)
                progressDialog.dismiss()
            },
            {
                myAdapter.modify(it)
            }, 
            {
                myAdapter.remove(it)
            },
            {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    
}