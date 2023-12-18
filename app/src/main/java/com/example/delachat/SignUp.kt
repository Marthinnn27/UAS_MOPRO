package com.example.delachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtCPassword: EditText
    private lateinit var edtusername: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtusername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        edtCPassword = findViewById(R.id.edt_c_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        edtCPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val username = edtusername.text.toString()
            val password = edtPassword.text.toString()
            val confirm_password = edtCPassword.text.toString()

            if (password == confirm_password) {
                signUp(username, email, password)
            } else {
                Toast.makeText(this@SignUp, "Password and confirm password does not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun signUp(username: String, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(username,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(username: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().reference
        mDbRef.child("User").child(uid).setValue(User(username,email,uid))
    }

}