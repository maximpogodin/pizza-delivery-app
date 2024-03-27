package com.example.delivery_society.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.CurrentUser
import com.example.delivery_society.model.entities.User
import com.google.android.material.navigation.NavigationBarView

class RegistrationActivity : AppCompatActivity() {

    private var db = ApplicationDbContext()
    private lateinit var editUsername : EditText
    private lateinit var editPassword : EditText
    private lateinit var editFirstName : EditText
    private lateinit var editLastName : EditText
    private lateinit var editMiddleName : EditText
    private lateinit var editPhoneNumber : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        var view = findViewById<NavigationBarView>(R.id.register_view) as LinearLayout
        val buttonRegister = view.findViewById(R.id.button_register) as Button
        editUsername = view.findViewById(R.id.edit_username)
        editPassword = view.findViewById(R.id.edit_password)
        editFirstName = view.findViewById(R.id.edit_first_name)
        editLastName = view.findViewById(R.id.edit_last_name)
        editMiddleName = view.findViewById(R.id.edit_middle_name)
        editPhoneNumber = view.findViewById(R.id.edit_phone_number)

        buttonRegister.setOnClickListener { _ ->
            register()
        }

        supportActionBar?.title = "Регистрация"
    }

    private fun register() {
        if (editUsername.text.isEmpty()) {
            Toast.makeText(this, "Заполните имя пользователя", Toast.LENGTH_SHORT).show()
            return
        }
        if (editPassword.text.isEmpty()) {
            Toast.makeText(this, "Укажите пароль пользователя", Toast.LENGTH_SHORT).show()
            return
        }
        if (editLastName.text.isEmpty()) {
            Toast.makeText(this, "Укажите фамилию", Toast.LENGTH_SHORT).show()
            return
        }
        if (editFirstName.text.isEmpty()) {
            Toast.makeText(this, "Укажите имя", Toast.LENGTH_SHORT).show()
            return
        }
        if (editPhoneNumber.text.isEmpty()) {
            Toast.makeText(this, "Укажите номер телефона", Toast.LENGTH_SHORT).show()
            return
        }

        val user = db.getUser(editUsername.text.toString())

        if (user != null) {
            Toast.makeText(this, "Пользователь с заданным именем уже существует.", Toast.LENGTH_SHORT).show()
            return
        }

        db.createUser(User(0, editFirstName.text.toString(), editLastName.text.toString(),
            editMiddleName.text.toString(), editUsername.text.toString(), editPassword.text.toString(),
            editPhoneNumber.text.toString(), 1, 2))
        Toast.makeText(this, "Вы были успешно зарегистрированы.", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, AuthorizationActivity::class.java));
        finish()
    }
}