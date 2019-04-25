package by.lebedev.nanopoolmonitoring.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.news.News
import by.lebedev.nanopoolmonitoring.activities.news.ServiceGeneratorNews.createNews
import by.lebedev.nanopoolmonitoring.activities.news.adapterNews.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_five.*

class NewsFragment : Fragment() {
    private lateinit var adapterNews: NewsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_five,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNews()
    }

    fun getNews() {

        val disposablesNews = createNews().loadNews("6bb2d102680be65b5d540eb15eb2c58a")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultNews ->
                setupRecyclerNews(resultNews)
                Log.e("AAA", "забрал лист новостей")
            },
                { error -> Log.e("AAA", "не забрал новости!!!") })
    }

    fun setupRecyclerNews(listNews: List<News>) {

        recycleViewNews.layoutManager = LinearLayoutManager(context)

        adapterNews = NewsAdapter(listNews,context)

        recycleViewNews.adapter = adapterNews
    }

}
