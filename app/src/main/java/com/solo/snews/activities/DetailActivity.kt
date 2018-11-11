package com.solo.snews.activities

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.solo.snews.R
import kotlinx.android.synthetic.main.activity_detail.*
import com.solo.snews.models.NewsModel
import com.squareup.picasso.Picasso
import io.realm.Realm


class DetailActivity : AppCompatActivity() {
    lateinit var mRealm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mRealm = Realm.getDefaultInstance()
        val newsModel = intent.getParcelableExtra<NewsModel>("news")
        Picasso.get().load(newsModel.newsFields?.thumbnail).into(mImage)
        mTittlele.text = newsModel.title

        if (newsModel.isSelected){
            pinImg.setImageResource(R.drawable.pin_red)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentText.text = Html.fromHtml(newsModel.newsFields?.content,Html.FROM_HTML_MODE_COMPACT)

        } else {
            contentText.text = Html.fromHtml(newsModel.newsFields?.content)
        }



        pinImg.setOnClickListener {
            if (newsModel.isSelected){
                pinImg.setImageResource(R.drawable.pin)
                newsModel.isSelected = false
                removeFromRealm(newsModel)
            } else {
                pinImg.setImageResource(R.drawable.pin_red)
                newsModel.isSelected = true
                saveToRealm(newsModel)
            }
        }

    }

    private fun saveToRealm(newsModel: NewsModel){
        mRealm.beginTransaction()
        mRealm.copyToRealm(newsModel)
        mRealm.commitTransaction()
    }

    private fun removeFromRealm(newsModel: NewsModel){
        mRealm.beginTransaction()
        mRealm.where(NewsModel::class.java).equalTo("title",newsModel.title).findFirst()?.deleteFromRealm()
        mRealm.commitTransaction()
    }

}
