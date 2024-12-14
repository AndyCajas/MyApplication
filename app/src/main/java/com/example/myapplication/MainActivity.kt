package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var tvFeedback: TextView
    private lateinit var etGuess: EditText
    private lateinit var btnGuess: Button
    private lateinit var tvRemainingAttempts: TextView
    private lateinit var btnRestart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        tvFeedback = findViewById(R.id.tvFeedback)
        etGuess = findViewById(R.id.etGuess)
        btnGuess = findViewById(R.id.btnGuess)
        tvRemainingAttempts = findViewById(R.id.tvRemainingAttempts)
        btnRestart = findViewById(R.id.btnRestart)

        GameLogic.generateRandomNumber()
        updateRemainingAttemptss()

        btnGuess.setOnClickListener {
            val guess = etGuess.text.toString().toIntOrNull()
            if (guess != null && guess in GameLogic.MIN_NUMBER..GameLogic.MAX_NUMBER) {
                when (GameLogic.checkGuess(guess)) {
                    GameLogic.FeedbackResult.CORRECT -> {
                        tvFeedback.text = "¡Correcto! Has adivinado el número."
                        disableGame()
                    }
                    GameLogic.FeedbackResult.TOO_LOW -> tvFeedback.text = "Demasiado bajo. Intenta con un número más alto."
                    GameLogic.FeedbackResult.TOO_HIGH -> tvFeedback.text = "Demasiado alto. Intenta con un número más bajo."
                }
                updateRemainingAttemptss()
                if (GameLogic.getRemainingAttemptsf() == 0 && guess != GameLogic.randomNumber) {
                    tvFeedback.text = "Has perdido. El número era ${GameLogic.randomNumber}."
                    disableGame()
                }
            } else {
                tvFeedback.text = "Entrada inválida. Ingresa un número entre ${GameLogic.MIN_NUMBER} y ${GameLogic.MAX_NUMBER}."
            }
            etGuess.text.clear()
        }

        btnRestart.setOnClickListener {
            GameLogic.resetGame()
            enableGame()
            tvFeedback.text = "I'm thinking of a number between ${GameLogic.MIN_NUMBER} and ${GameLogic.MAX_NUMBER}."
            updateRemainingAttemptss()
        }
    }

    private fun updateRemainingAttemptss() {
        tvRemainingAttempts.text = "Remaining Attempts: ${GameLogic.getRemainingAttemptsf()}"
    }

    private fun disableGame() {
        btnGuess.isEnabled = false
        etGuess.isEnabled = false
    }

    private fun enableGame() {
        btnGuess.isEnabled = true
        etGuess.isEnabled = true
    }
}