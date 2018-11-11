package com.solo.snews.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.solo.snews.api.RetrofitFactory
import com.solo.snews.models.NewsModel
import com.solo.snews.models.RequestModel
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import org.json.JSONObject
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.solo.snews.R
import com.solo.snews.adapters.NewsAdapter
import com.solo.snews.adapters.PinsAdapter
import com.solo.snews.listeners.EndlessRecyclerOnScrollListener
import com.solo.snews.servieces.NotificationJob
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var adapter: NewsAdapter
    private lateinit var pinsAdapter: PinsAdapter
    private val requestModel = RequestModel()
    private var pinedNews = ArrayList<NewsModel>()
    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NotificationJob.scheduleJob()
        mRealm = Realm.getDefaultInstance()
        pinsRecycler.layoutManager = LinearLayoutManager(this,LinearLayout.HORIZONTAL,false)
        pinsRecycler.setHasFixedSize(true)
        pinsAdapter = PinsAdapter(this)
        pinsRecycler.adapter = pinsAdapter

        getPinedNews()
        pinsAdapter.news = pinedNews


        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter = NewsAdapter(this)
        mRecyclerView.adapter = adapter
        getNews()

        swipeRefreshLay.setOnRefreshListener {
            adapter.news.clear()
            requestModel.currentPage = 1
            getNews()
        }
        mRecyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                requestModel.currentPage++
                getNews()
            }
        })


    }

    private fun getPinedNews(){
        val news = mRealm.where(NewsModel::class.java).findAll()
        pinedNews.clear()
        for(n in news){
            pinedNews.add(mRealm.copyFromRealm(n))
        }
        pinsAdapter.news = pinedNews
        pinsAdapter.notifyDataSetChanged()

        if (news.isEmpty()){
            pinsLay.visibility = View.GONE
            line.visibility = View.GONE
        } else {
            pinsLay.visibility = View.VISIBLE
            line.visibility = View.VISIBLE
        }

    }

    private fun getNews() {
        val retrofitFactory = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = retrofitFactory.getNews(requestModel.format, requestModel.useDate, requestModel.pages, requestModel.currentPage.toString(), requestModel.fields, requestModel.key)
                val response = request.execute()

                withContext(kotlinx.coroutines.experimental.Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val jsonObject = JSONObject(response.body()?.string())
                        val responseObject = jsonObject.getJSONObject("response")
                        val newsArray = responseObject.getString("results")
                        val news = Gson().fromJson<Array<NewsModel>>(newsArray, Array<NewsModel>::class.java)
                        adapter.news.addAll(news)
                        checkPinItems()
                        adapter.notifyDataSetChanged()
                        swipeRefreshLay.isRefreshing = false


                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun checkPinItems(){
        for (n in adapter.news){
            if (pinedNews.contains(n)){
                n.isSelected = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getPinedNews()
        checkPinItems()
    }
}
