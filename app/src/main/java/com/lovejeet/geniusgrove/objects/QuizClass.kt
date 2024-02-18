package com.lovejeet.geniusgrove.objects

import android.content.Intent
import com.lovejeet.geniusgrove.activity.MainActivity
import com.lovejeet.geniusgrove.activity.QuizActivity
import com.lovejeet.geniusgrove.models.Category
import com.lovejeet.geniusgrove.models.QuestionStats
import com.lovejeet.geniusgrove.models.QuizResponse
import com.lovejeet.geniusgrove.retrofit.QuestionStatsService
import com.lovejeet.geniusgrove.retrofit.QuizService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizClass(private val context: MainActivity) {

    fun getQuizList(amount: Int,category: Int?,difficulty:String?,type:String?){

//        var questionList = listOf<QuizQuestions>()
        if(Constants.isNetworkAvailable(context))
        {
            val pbDialog = Utils.showProgressBar(context)
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val service:QuizService = retrofit.create(QuizService::class.java)

            val dataClass : Call<QuizResponse> = service.getQuiz(
                amount,category, difficulty, type
            )

            dataClass.enqueue(object : Callback<QuizResponse>{

                override fun onResponse(
                    call: Call<QuizResponse>,
                    response: Response<QuizResponse>
                ) {
                    pbDialog.cancel()
                    if(response.isSuccessful){
                        val responseData : QuizResponse = response.body()!!
                        val questionList = ArrayList(responseData.results)
                       if(questionList.isNotEmpty())
                       {
                           val intent = Intent(context,QuizActivity::class.java)
                           intent.putExtra("questionList",questionList)
                           context.startActivity(intent)
                       }
                    }
                    else{
                        Utils.showToast(context,"Response Failed")
                    }
                }

                override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                    pbDialog.cancel()
                    Utils.showToast(context,"Failure in Response")
                }

            })
        }
        else{
            Utils.showToast(context,"Network is not available")
        }

    }

    fun getQuestionStatsList(callback: QuestionStatsCallback){
        if(Constants.isNetworkAvailable(context))
        {
            val pbDialog = Utils.showProgressBar(context)
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val service:QuestionStatsService =  retrofit.create(QuestionStatsService::class.java)
            val dataClass : Call<QuestionStats> = service.getData()

            dataClass.enqueue(object : Callback<QuestionStats>{

                override fun onResponse(
                    call: Call<QuestionStats>,
                    response: Response<QuestionStats>
                ) {
                    pbDialog.cancel()
                    if(response.isSuccessful){
                        val questionStats : QuestionStats = response.body()!!
                        val categoryMap = questionStats.categories
                        callback.onQuestionStatsFetched(categoryMap)
                    }
                    else{
                        Utils.showToast(context,"Error Code : ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<QuestionStats>, t: Throwable) {
                    pbDialog.cancel()
                    Utils.showToast(context,"API Call Failure")
                }

            })
        }
        else
        {
            Utils.showToast(context,"Network is Not Available")
        }
    }

    interface  QuestionStatsCallback{
        fun onQuestionStatsFetched(map:Map<String,Category>)
    }
}