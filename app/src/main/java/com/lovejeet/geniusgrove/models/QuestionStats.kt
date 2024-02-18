package com.lovejeet.geniusgrove.models

data class QuestionStats(
    val overall :Overall,
    val categories : Map<String,Category>
)

data class Overall(
    val total_num_of_questions: Int,
    val total_num_of_pending_questions: Int,
    val total_num_of_verified_questions: Int,
    val total_num_of_rejected_questions: Int
)

data class Category(
    val total_num_of_questions: Int,
    val total_num_of_pending_questions: Int,
    val total_num_of_verified_questions: Int,
    val total_num_of_rejected_questions: Int
)
