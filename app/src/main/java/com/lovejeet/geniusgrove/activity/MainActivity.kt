package com.lovejeet.geniusgrove.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.lovejeet.geniusgrove.adapters.GridViewAdapter
import com.lovejeet.geniusgrove.databinding.ActivityMainBinding
import com.lovejeet.geniusgrove.models.Category
import com.lovejeet.geniusgrove.objects.Constants
import com.lovejeet.geniusgrove.objects.QuizClass

class MainActivity : AppCompatActivity() {

    var binding : ActivityMainBinding? = null
    val quizClass = QuizClass(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val rvCategoryList = binding?.rvCategoryList
        rvCategoryList?.layoutManager = GridLayoutManager(this,2)
        quizClass.getQuestionStatsList(object :QuizClass.QuestionStatsCallback{
            override fun onQuestionStatsFetched(map: Map<String, Category>) {
                val adapter = GridViewAdapter(Constants.getCategoryItemList(),map)
                rvCategoryList?.adapter = adapter
                adapter.setOnTouchResponse(object : GridViewAdapter.OnTouchResponse{
                    override fun onClick(id: Int) {
                      quizClass.getQuizList(10,id,null,null)
                    }
                })
            }
        })

        binding?.btnRandomQuiz?.setOnClickListener{
            quizClass.getQuizList(10,null,null,null)
        }
          }
}