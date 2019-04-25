package by.lebedev.nanopoolmonitoring.activities.news

import android.view.View

data class News (
    val title: String,
    val originalImageUrl: String

)
data class NewsFull (
    val title: String,
    val originalImageUrl: String,
    val url:String
)