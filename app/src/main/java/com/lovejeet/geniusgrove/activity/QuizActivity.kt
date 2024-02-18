package com.lovejeet.geniusgrove.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.core.content.ContextCompat
import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.databinding.ActivityMainBinding
import com.lovejeet.geniusgrove.databinding.ActivityQuizBinding
import com.lovejeet.geniusgrove.models.QuizQuestions
import com.lovejeet.geniusgrove.models.ResultModel
import com.lovejeet.geniusgrove.objects.Constants
import com.lovejeet.geniusgrove.objects.Utils

class QuizActivity : AppCompatActivity() {
    private var binding : ActivityQuizBinding? = null
    private lateinit var questionList : ArrayList<QuizQuestions>
    private var position = 0
    var allowPlaying = true
    private var timer : CountDownTimer ?= null
    private var timeLeft = 0
    private var score = 0.0

    private var resultList = ArrayList<ResultModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        questionList = intent.getSerializableExtra("questionList")
        as ArrayList<QuizQuestions>

        binding?.pbProgress?.max = questionList.size
        setQuestion()
        setOptions()
        startTimer()
        binding?.tvProgress?.text = "1/${questionList.size}"
        resetButtonBg()
        binding?.btnNext?.setOnClickListener{
            onNext()
        }

        val redBg = ContextCompat.getDrawable(this,R.drawable.red_button_bg)
        val optionClick =  OnClickListener{view->
            if(allowPlaying)
            {
                timer?.cancel()
                view.background = redBg
                showCorrectAns()
                setScore(view as Button?)
                allowPlaying = true
            }
        }
        binding?.option1?.setOnClickListener(optionClick)
        binding?.option2?.setOnClickListener(optionClick)
        binding?.option3?.setOnClickListener(optionClick)
        binding?.option4?.setOnClickListener(optionClick)
    }

    private fun setQuestion()
    {
        val decodeQuestion =Constants.decodeHtmlString(questionList[position].question)
        binding?.tvQuestion?.text = decodeQuestion
    }
    private lateinit var correctAns : String
    private lateinit var optionList : List<String>
    private fun setOptions()
    {
        val question = questionList[position]
        val temp = Constants.getRandomOptions(question.correct_answer,question.incorrect_answers)
        optionList = temp.second
        correctAns = temp.first

        binding?.option1?.text = optionList[0]
        binding?.option2?.text = optionList[1]

      if(question.type == "multiple")
      {
          binding?.option3?.visibility = View.VISIBLE
          binding?.option4?.visibility = View.VISIBLE

          binding?.option3?.text = optionList[2]
          binding?.option4?.text = optionList[3]
      }
        else
      {
          binding?.option3?.visibility = View.GONE
          binding?.option4?.visibility = View.GONE
      }
    }

    private fun onNext()
    {
        val resultModel = ResultModel(
            20-timeLeft,
            questionList[position].type,
            questionList[position].difficulty,
            score
        )
        resultList.add(resultModel)
        score = 0.0

        if(position<questionList.size-1)
        {
            timer?.cancel()
            position++
            setQuestion()
            setOptions()
            binding?.pbProgress?.progress = position+1
            binding?.tvProgress?.text = "${position+1}/${questionList.size}"
            resetButtonBg()
            allowPlaying = true
            startTimer()
        }
        else
        {
            endGame()
        }
    }

    private fun startTimer()
    {
        binding?.circularProgressBar?.max = 20
        binding?.circularProgressBar?.progress = 20
        timer = object :CountDownTimer(20000,1000){
            override fun onTick(remaining: Long) {
                binding?.circularProgressBar?.incrementProgressBy(-1)
                binding?.tvTimer?.text = (remaining/1000).toString()
                timeLeft = (remaining/1000).toInt()
            }

            override fun onFinish() {
                showCorrectAns()
                onNext()
                allowPlaying = false
            }

        }.start()
    }

    private fun showCorrectAns()
    {
        val greenBg = ContextCompat.getDrawable(this,R.drawable.green_button_bg)
        when(true)
        {
            (correctAns == optionList[0])-> binding?.option1?.background = greenBg
            (correctAns == optionList[1])-> binding?.option2?.background = greenBg
            (correctAns == optionList[2])-> binding?.option3?.background = greenBg
            else-> binding?.option4?.background = greenBg
        }
    }

    private fun resetButtonBg(){
        val grayBg = ContextCompat.getDrawable(this,R.drawable.button)
        binding?.option1?.background = grayBg
        binding?.option2?.background = grayBg
        binding?.option3?.background = grayBg
        binding?.option4?.background = grayBg
    }

    private fun setScore(button: Button?)
    {
        if(correctAns == button?.text)
        {
            score = getScore()
        }
    }
    private fun getScore():Double
    {
        val score1 = when(questionList[position].type) {
            "boolean" -> 0.5
            else -> 1.0
        }
        val score2 : Double = (timeLeft.toDouble())/(20).toDouble()
        val score3 = when(questionList[position].difficulty){
            "easy" -> 1.0
            "medium" -> 2.0
            else -> 3.0
        }
        return score1 + score2 + score3
    }
    private fun endGame()
    {
        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("resultList",resultList)
        startActivity(intent)
        finish()
    }
}
