package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.CorrectionInfo
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.max

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition : Int = 1
    private var mQuestionsList : ArrayList<Questions>? = null
    private var mSelectedOptionPosition : Int = 0

    private var mUserName : String ? = null
    private var mCorrectAnswer : Int = 0

    private var progressBar : ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionfour: TextView? = null
    private var btnSubmit : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionfour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_Submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionfour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)


        mQuestionsList = Constants.getQuestions()

        setQuestion()
        defaultOptionsView()


    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        // mCurrentPosition = 1
        defaultOptionsView()
        val question: Questions = mQuestionsList!![mCurrentPosition - 1]

        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.questions
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionfour?.text = question.optionFour

        if(mCurrentPosition == mQuestionsList!!.size)
        {
            btnSubmit?.text = "FINISH"
        }
        else {
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun selectedOptionView(tv : TextView , selectedOptionNum : Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface , Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )

    }


    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let{
            options.add(0,it)
        }
        tvOptionTwo?.let{
            options.add(1,it)
        }
        tvOptionThree?.let{
            options.add(2,it)
        }
        tvOptionfour?.let{
            options.add(3,it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#7a8089"))
            //option.setTextColor(Color.parseColor("#FF0000"))
            option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
                //  R.drawable.selected_option_border_bg
            )
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.tv_option_four -> {
                tvOptionfour?.let {
                    selectedOptionView(it, 4)
                }
            }


            R.id.btn_Submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else->{
                            // Toast.makeText(this,"Congrats !! You Have finished it." , Toast.LENGTH_LONG).show()

                            val intent = Intent(this , ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME , mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWER , mCorrectAnswer)
                            intent.putExtra(Constants.TOTAL_QUESTIONS , mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            } else-> {
            val questions = mQuestionsList?.get(mCurrentPosition - 1)
            if (questions!!.correctAnswer != mSelectedOptionPosition) {

                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            }else{
                mCorrectAnswer++
            }
            answerView(questions.correctAnswer, R.drawable.correct_option_border_bg)
            if (mCurrentPosition == mQuestionsList!!.size) {
                btnSubmit?.text = "Finish"
            } else {
                btnSubmit?.text = "Go to Next Question"
            }

            mSelectedOptionPosition = 0

        }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer){
            1-> {
                tvOptionOne?.background =ContextCompat.getDrawable(
                    this,drawableView
                )
            }

            2-> {
                tvOptionTwo?.background =ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,drawableView
                )
            }

            3-> {
                tvOptionThree?.background =ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,drawableView
                )
            }

            4-> {
                tvOptionfour?.background =ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,drawableView
                )
            }

        }
    }
}
