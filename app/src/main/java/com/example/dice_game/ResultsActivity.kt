package com.example.dice_game

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val resultsList = findViewById<ListView>(R.id.results_list)
        val backButton = findViewById<Button>(R.id.back_button)

        // Получение данных из интента
        val results = intent.getStringArrayListExtra("GAME_RESULTS") ?: arrayListOf()

        // Установка данных в список
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
        resultsList.adapter = adapter

        // Обработчик кнопки "Назад"
        backButton.setOnClickListener {
            finish() // Возвращаемся к предыдущей активности
        }
    }
}
