package by.lebedev.nanopoolmonitoring.fragments.news.recycler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.fragments.news.retrofit.entity.News
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
            val address = Uri.parse(listNews.get(holder.adapterPosition).url);
            val openlink = Intent(Intent.ACTION_VIEW, address);
            it.context.startActivity(openlink)
        }
        return holder

    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holderNews: ViewHolderNews, position: Int) {
        listNews.let {
            val news = it[position]
            holderNews.viewNews.titleNews.text = news.title
            Picasso.get().load(news.originalImageUrl)
                .into(holderNews.viewNews.urlNews)


        }
    }

}
