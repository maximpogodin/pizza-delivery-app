package com.example.delivery_society.view

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.delivery_society.R
import com.example.delivery_society.api.Api
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.api.Database
import com.example.delivery_society.model.entities.CurrentUser
import com.google.android.material.navigation.NavigationBarView

class AuthorizationActivity : AppCompatActivity() {

    private var db = ApplicationDbContext()
    private lateinit var editUsername : EditText
    private lateinit var editPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorize)

        var loginView = findViewById<NavigationBarView>(R.id.login_view) as LinearLayout
        val buttonLogin = loginView.findViewById(R.id.button_login) as Button
        val buttonRegister = loginView.findViewById(R.id.button_register) as Button
        editUsername = loginView.findViewById(R.id.edit_username)
        editPassword = loginView.findViewById(R.id.edit_password)

        buttonLogin.setOnClickListener { _ ->
            login()
        }
        buttonRegister.setOnClickListener { _ ->
            startActivity(Intent(this, RegistrationActivity::class.java));
            finish()
        }

        supportActionBar?.title = "Авторизация"

        Database.filePath = Api().getFileFromAssets(this, "DeliverySociety.db").absolutePath
    }

    private fun login() {
        if (editUsername.text.isEmpty()) {
            Toast.makeText(this, "Заполните имя пользователя", Toast.LENGTH_SHORT).show()
            return
        }
        if (editPassword.text.isEmpty()) {
            Toast.makeText(this, "Укажите пароль пользователя", Toast.LENGTH_SHORT).show()
            return
        }

        val user = db.getUser(editUsername.text.toString())

        if (user == null) {
            Toast.makeText(this, "Пользователь с заданным именем не существует.", Toast.LENGTH_SHORT).show()
            return
        }
        if (user.password != editPassword.text.toString()) {
            Toast.makeText(this, "Указан неверный пароль", Toast.LENGTH_SHORT).show()
            return
        }

        CurrentUser.user = user

        if (user.access_right_id == 1) {
            startActivity(Intent(this, EmployeeActivity::class.java));
        }
        else if (user.access_right_id == 2) {
            startActivity(Intent(this, ClientActivity::class.java));
        }
        else
        {
            Toast.makeText(this, "У Вас нет прав доступа к системе", Toast.LENGTH_SHORT).show()
            return
        }

        finish()
    }
}