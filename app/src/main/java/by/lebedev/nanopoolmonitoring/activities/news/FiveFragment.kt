package by.lebedev.nanopoolmonitoring.activities.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R

import by.lebedev.nanopoolmonitoring.activities.news.adapterNews.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_five.*




class FiveFragment : Fragment() {

    private lateinit var adapterNews: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_five,container,false)
    }

    override fun onViewCreated(viewNews: View, savedInstanceState: Bundle?) {
        super.onViewCreated(viewNews, savedInstanceState)
//        getNews()

        val listNews = ArrayList<News>()
        listNews.add(News("Horse", "urlm,mmmmn"))
        listNews.add(News("Horse1", "urlm,mmmmn"))
        listNews.add(News("Horse2", "urlm,mmmmn"))

        setupRecyclerNews(listNews)

    }


//    fun getNews() {
//        val disposablesNews = ServiceGeneratorNews.createNews().loadNews()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ resultNews ->
//                resultNews.map {
//                    it
//                }
//                setupRecyclerNews(resultNews)
//                Log.e("AAA", "забрал лист новостей")
//            },
//                { error -> Log.e("AAA", "не забрал новости!!!") })
//    }


     fun setupRecyclerNews(listNews: List<News>) {

         recycleViewNews.layoutManager = LinearLayoutManager(context)

        adapterNews = NewsAdapter(listNews)

        recycleViewNews.adapter = adapterNews
    }
}