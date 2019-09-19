package by.lebedev.nanopoolmonitoringnoads.fragments.news.recycler

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.activities.webview.WebActivity
import by.lebedev.nanopoolmonitoringnoads.fragments.news.retrofit.entity.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter(
    val listNews: List<News>,
    context: Context?
) : RecyclerView.Adapter<ViewHolderNews>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNews {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val holder = ViewHolderNews(view)
        view.setOnClickListener {

            val url = listNews.get(holder.adapterPosition).url
            val intent = Intent(it.context, WebActivity::class.java)
            intent.putExtra("url", url)
            it.context.startActivity(intent)

        }
        return holder

    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holderNews: ViewHolderNews, position: Int) {
        listNews.let {
            val news = it[position]
            holderNews.viewNews.newsSource.text = news.sourceDomain
            holderNews.viewNews.newsPublishDate.text = news.publishedAt.substring(0,10)
            holderNews.viewNews.titleNews.text = news.title
            Picasso.get().load(news.originalImageUrl)
                .into(holderNews.viewNews.urlNews)

        }
    }
}