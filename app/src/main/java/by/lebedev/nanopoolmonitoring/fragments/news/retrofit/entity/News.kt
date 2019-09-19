package by.lebedev.nanopoolmonitoring.fragments.news.retrofit.entity

data class News (
    val title: String,
    val publishedAt:String,
    val originalImageUrl: String,
    val sourceDomain:String,
    val url:String
)