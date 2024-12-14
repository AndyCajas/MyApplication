package com.example.myapplication

import kotlin.random.Random

object GameLogic {
    var randomNumber = 0
    var remainingAttempts = 10
    const val MIN_NUMBER = 1
    const val MAX_NUMBER = 100

    fun generateRandomNumber() {
        randomNumber = Random.nextInt(MIN_NUMBER, MAX_NUMBER + 1)
    }

    fun checkGuess(guess: Int): FeedbackResult {
        remainingAttempts--
        return when {
            guess < randomNumber -> FeedbackResult.TOO_LOW
            guess > randomNumber -> FeedbackResult.TOO_HIGH
            else -> FeedbackResult.CORRECT
        }
    }

    fun getRemainingAttemptsf(): Int = remainingAttempts

    fun resetGame() {
        remainingAttempts = 10
        generateRandomNumber()
    }

    enum class FeedbackResult {
        CORRECT, TOO_LOW, TOO_HIGH
    }
}