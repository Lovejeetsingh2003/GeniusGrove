package com.lovejeet.geniusgrove.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.adapters.QuizSummaryAdapter
import com.lovejeet.geniusgrove.databinding.ActivityResultBinding
import com.lovejeet.geniusgrove.models.ResultModel

class ResultActivity : AppCompatActivity() {
    private var binding: ActivityResultBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val resultList : ArrayList<ResultModel> = intent.getSerializableExtra("resultList")
                as ArrayList<ResultModel>

        binding?.rvSummery?.layoutManager = LinearLayoutManager(this)
        val adapter = QuizSummaryAdapter(resultList)
        binding?.rvSummery?.adapter = adapter

        binding?.tvTotalScore?.text = getFinalScore(resultList).toString()

        binding?.btnHome?.setOnClickListener{
            finish()
        }
    }

    private fun getFinalScore(list: ArrayList<ResultModel>):Double
    {
        var result = 0.0
        for(i in list)
            result += i.score
        return result
    }
}