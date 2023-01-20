package com.example.bigbangtheory_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var prevButton: ImageButton

    private val questionBank = listOf(
        Question(R.string.question_roommate, true),
        Question(R.string.question_girlfriend, false),
        Question(R.string.question_rajesh_mom, false),
        Question(R.string.question_rajesh_mutism, false),
        Question(R.string.question_emmy_neuroscientist, true),
        Question(R.string.question_howard_doctorate, false),
        Question(R.string.question_comic_books, true))

    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView =  findViewById(R.id.question_text_View)

        trueButton.setOnClickListener {
            checkAnswer(true)
            isAnswered()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            isAnswered()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            isAnswered()
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()
            isAnswered()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            isAnswered()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        questionBank[currentIndex].isAnswered = true

        val messageResId = if (userAnswer == correctAnswer) {
            questionBank[currentIndex].userAnswer = true
            R.string.correct_toast
        } else {
            questionBank[currentIndex].userAnswer = false
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun isAnswered() {
        val question = questionBank[currentIndex]

        if (question.isAnswered) {
            falseButton.isEnabled = false
            trueButton.isEnabled = false
        } else {
            falseButton.isEnabled = true
            trueButton.isEnabled = true
        }

        if (questionBank.all { it.isAnswered }) {
            val rightAnswers = questionBank.filter { it.userAnswer == true }
            val accuracy: Double = ((rightAnswers.size).toDouble() / (questionBank.size.toDouble())) * 100.0
            val resultAccuracy = accuracy.toInt()


            questionTextView.text = getString(R.string.rightAnswers, resultAccuracy)
        }
    }
}