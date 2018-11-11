package com.solo.snews.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solo.snews.R
import com.solo.snews.activities.DetailActivity
import com.solo.snews.models.NewsModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item.view.*

class PinsAdapter(private val context: Context):  RecyclerView.Adapter<PinsAdapter.ViewHolder>() {
    var news : ArrayList<NewsModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.pin_item, parent, false)
        return ViewHolder(layoutView)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: ViewHolder, possition: Int) {
        Picasso.get().load(news[possition].newsFields?.thumbnail).into(holder.mImg)
        holder.mTittle.text = news[possition].title

        holder.mImg.setOnClickListener {
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("news",news[possition])
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var mImg = view.newsImg
        var mTittle = view.newsName
    }
}