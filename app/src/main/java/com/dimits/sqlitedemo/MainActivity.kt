package com.dimits.sqlitedemo

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.dimits.sqlitedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var databaseHelper: ArticleDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = ArticleDbHelper(applicationContext)

        setListeners()
    }

    private fun setListeners() {
        binding.submit.setOnClickListener {
            val name = binding.edtName.text.toString()
            val job = binding.edtJob.text.toString()
            val newEntry = ContentValues().apply {
                put(DB.NAME, name)
                put(DB.JOB, job)
            }
            databaseHelper.writableDatabase.insert(DB.TABLE_NAME, null, newEntry)
        }

        binding.getInfo.setOnClickListener {
            readData()
        }
    }

    private fun readData() {
        val cursor = databaseHelper.readableDatabase.rawQuery(
            "SELECT * FROM ${DB.TABLE_NAME}",
            arrayOf<String>()
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val body = cursor.getString(2)
            Log.d("MAIN_ACTIVITY", "$id - $title - $body")
        }
    }
}