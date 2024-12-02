package com.app.appforlearningenglish

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.app.appforlearningenglish.databinding.ActivityLearnWordBinding
import com.app.appforlearningenglish.ui.theme.AppForLearningEnglishTheme

class MainActivity : ComponentActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalArgumentException("Binding for ActivityLearnWordBinding must not be null")

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding){
            btnContinue.setOnClickListener{
                layoutResult.isVisible = false
                markAnswerNatural(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                markAnswerNatural(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                markAnswerNatural(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                markAnswerNatural(layoutAnswer4, tvVariantNumber4, tvVariantValue4)

                showNextQuestion(trainer)
            }
            btnSkip.setOnClickListener{
                showNextQuestion(trainer)
            }
        }
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with(binding){
            if(firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS){
                tvQuestionWord.isVisible = false
                layoutVariants.isVisible = false
                btnSkip.text = "Complete"
            }else{
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.original

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener{
                    if(trainer.checkAnswer(0)){
                        markAnswerCorrect(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(false)
                    }
                }

                layoutAnswer2.setOnClickListener{
                    if(trainer.checkAnswer(1)){
                        markAnswerCorrect(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(false)
                    }
                }

                layoutAnswer3.setOnClickListener{
                    if(trainer.checkAnswer(2)){
                        markAnswerCorrect(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(false)
                    }
                }

                layoutAnswer4.setOnClickListener{
                    if(trainer.checkAnswer(3)){
                        markAnswerCorrect(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(false)
                    }
                }


            }
        }
    }

    private fun markAnswerNatural(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers
        )

        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants
            )
            setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                )
            )
        }

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.textVariantsColor
            )
        )
    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ){
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswerColor
            )
        )
    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswerColor
            )
        )
    }

    private fun showResultMessage(isCorrect: Boolean){
        val color: Int
        val messageText: String
        val resultIconResource: Int
        if(isCorrect){
            color = ContextCompat.getColor(this, R.color.correctAnswerColor)
            resultIconResource = R.drawable.ic_correct
            messageText = "Correct!"
        }else{
            color = ContextCompat.getColor(this, R.color.wrongAnswerColor)
            resultIconResource = R.drawable.ic_wrong
            messageText = "Incorrect!"
        }

        with(binding){
            btnSkip.isVisible = false
            layoutResult.isVisible = true
            btnContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            tvResultMessage.text = messageText
            ivResultIcon.setImageResource(resultIconResource)
        }
    }
}