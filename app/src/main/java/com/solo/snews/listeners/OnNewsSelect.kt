package com.solo.snews.listeners

import com.solo.snews.models.NewsModel

interface OnNewsSelect {
    fun onItemSelect(item: NewsModel)
}