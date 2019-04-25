package by.lebedev.nanopoolmonitoring.activities.news

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.activities.news.ServiceGeneratorNews.createNews

import by.lebedev.nanopoolmonitoring.activities.news.adapterNews.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_five.*
import kotlinx.android.synthetic.main.item_coin.*
import kotlinx.android.synthetic.main.web_fragment.*
import android.widget.AdapterView
import kotlinx.android.synthetic.main.item_news.*
import java.text.FieldPosition


class FiveFragment : Fragment() {

    var API_KEY: String = "6bb2d102680be65b5d540eb15eb2c58a"

    private lateinit var adapterNews: NewsAdapter
    private lateinit var url: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_five, container, false)
    }


    override fun onViewCreated(viewNews: View, savedInstanceState: Bundle?) {
        super.onViewCreated(viewNews, savedInstanceState)
        getNews()


//        onFullStoryClicked(viewNews)
//        val listNews = ArrayList<News>()
//        listNews.add(News("Horse", "urlm,mmmmn"))
//        listNews.add(News("Horse1", "urlm,mmmmn"))
//        listNews.add(News("Horse2", "urlm,mmmmn"))

//        setupRecyclerNews(listNews)
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

        adapterNews = NewsAdapter(listNews)

        recycleViewNews.adapter = adapterNews


    }

//    fun onFullStoryClicked(url: String) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setData(Uri.parse(url))
//        startActivity(intent)
//    }
}