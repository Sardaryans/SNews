package com.solo.snews.activities

import android.app.Application
import io.realm.Realm
import com.evernote.android.job.JobManager
import com.solo.snews.servieces.DemoJobCreator


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        JobManager.create(this).addJobCreator(DemoJobCreator())


    }
}