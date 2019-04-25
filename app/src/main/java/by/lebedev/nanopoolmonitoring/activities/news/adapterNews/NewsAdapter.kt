package by.lebedev.nanopoolmonitoring.activities.news.adapterNews

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.news.News
import by.lebedev.nanopoolmonitoring.activities.news.ViewHolderNews
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter(val listNews: List<News>) : RecyclerView.Adapter<ViewHolderNews>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews {
        return ViewHolderNews(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holderNews: ViewHolderNews, position: Int) {
        listNews.let {
            val news = it[position]
            holderNews.viewNews.titleNews.text = news.title
            Picasso.get().load(news.originalImageUrl.toString())
                .into(holderNews.viewNews.urlNews)



        }
    }
}
