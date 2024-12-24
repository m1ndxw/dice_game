package com.example.dice_game

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var rollButton: Button
    private lateinit var resultText: TextView
    private lateinit var diceImage1: ImageView
    private lateinit var diceImage2: ImageView
    private lateinit var playAgainButton: Button
    private lateinit var inputField: EditText
    private lateinit var resultsButton: Button

    private val gameResults = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rollButton = findViewById(R.id.roll_button)
        resultText = findViewById(R.id.result_text)
        diceImage1 = findViewById(R.id.dice_image_1)
        diceImage2 = findViewById(R.id.dice_image_2)
        playAgainButton = findViewById(R.id.play_again_button)
        inputField = findViewById(R.id.input_field)
        resultsButton = findViewById(R.id.results_button)

        rollButton.setOnClickListener {
            val userInput = inputField.text.toString().toIntOrNull()
            if (userInput == null || userInput < 2 || userInput > 12) {
                resultText.text = "Введите число от 2 до 12"
                return@setOnClickListener
            }

            inputField.visibility = View.GONE
            rollButton.visibility = View.GONE

            startDiceAnimation(diceImage1, diceImage2)

            diceImage1.postDelayed({
                val diceRoll1 = rollDice()
                val diceRoll2 = rollDice()

                diceImage1.setImageResource(getDiceImage(diceRoll1))
                diceImage2.setImageResource(getDiceImage(diceRoll2))

                val totalRoll = diceRoll1 + diceRoll2
                resultText.text = if (totalRoll == userInput) {
                    "Поздравляем! Вы угадали!"
                } else {
                    "Вы выбросили: $totalRoll. Попробуйте снова!"
                }

                playAgainButton.visibility = Button.VISIBLE
            }, 3000)
        }

        playAgainButton.setOnClickListener {
            val gameResult = if (resultText.text.contains("Поздравляем")) "Выигрыш" else "Проигрыш"
            val userGuess = inputField.text.toString()

            val totalRoll = resultText.text.toString()
                .substringAfter("Вы выбросили: ")
                .substringBefore(".")

            gameResults.add("Ваш ответ: $userGuess, Итог: $totalRoll, Результат: $gameResult")

            resetGame()
        }


        resultsButton.setOnClickListener {
            val intent = Intent(this, ResultsActivity::class.java)
            intent.putStringArrayListExtra("GAME_RESULTS", gameResults)
            startActivity(intent)
        }
    }

    private fun rollDice(): Int {
        return Random.nextInt(1, 7)
    }

    private fun getDiceImage(diceRoll: Int): Int {
        return when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun startDiceAnimation(vararg diceImages: ImageView) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.rotate)
        for (image in diceImages) {
            image.setImageResource(R.drawable.dice_0)
            image.startAnimation(animation)
        }
    }

    private fun resetGame() {
        resultText.text = "Нажмите, чтобы начать!"
        playAgainButton.visibility = Button.GONE
        rollButton.visibility = View.VISIBLE
        inputField.visibility = View.VISIBLE
        inputField.text.clear()
        diceImage1.setImageResource(R.drawable.dice_0)
        diceImage2.setImageResource(R.drawable.dice_0)
    }
}
