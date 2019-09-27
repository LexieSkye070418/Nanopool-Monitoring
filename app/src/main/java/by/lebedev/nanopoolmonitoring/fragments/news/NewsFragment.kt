package by.lebedev.nanopoolmonitoring.fragments.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.fragments.news.recycler.NewsAdapter
import by.lebedev.nanopoolmonitoring.fragments.news.retrofit.ServiceGeneratorNews.createNews
import by.lebedev.nanopoolmonitoring.fragments.news.retrofit.entity.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {
    private lateinit var adapterNews: NewsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity()?.getWindow()?.setBackgroundDrawableResource(by.lebedev.nanopoolmonitoring.R.drawable.nanopool_background)

        getNews()
    }

    fun getNews() {

        val disposablesNews = createNews().loadNews(getString(R.string.news_key))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultNews ->
                if (resultNews!=null&&!resultNews.isEmpty()) {
                    setupRecyclerNews(resultNews)
                    progressNewsLoad.visibility = View.INVISIBLE
                }
            },
                { error -> Log.e("AAA", "не забрал новости!!!") })
    }

    fun setupRecyclerNews(listNews: List<News>) {
        recycleViewNews.layoutManager = LinearLayoutManager(context)
        adapterNews = NewsAdapter(listNews, context)
        recycleViewNews.adapter = adapterNews
    }
}
