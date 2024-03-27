package com.example.delivery_society.api
import android.content.Context
import android.os.StrictMode
import org.ktorm.database.Database
import java.io.File
import java.io.IOException
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager

class Api {
    fun getConnection() : Database {
        Class.forName("org.sqlite.JDBC").newInstance()
        DriverManager.registerDriver(
            Class.forName("org.sqldroid.SQLDroidDriver").newInstance() as Driver
        )
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val url = "jdbc:sqldroid:${com.example.delivery_society.api.Database.filePath}"
        val connection = DriverManager.getConnection(url)
        val database = Database.connect {
            object : Connection by connection {
                override fun close() {
                }
            }
        }

        return database
    }

    @Throws(IOException::class)
    fun getFileFromAssets(context: Context, fileName: String): File = File(context.cacheDir, fileName)
        .also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fileName).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }
}