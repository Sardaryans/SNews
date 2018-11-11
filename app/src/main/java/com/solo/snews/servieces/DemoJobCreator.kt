package com.solo.snews.servieces

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.solo.snews.servieces.NotificationJob


class DemoJobCreator : JobCreator {
    override fun create(tag: String): Job? {
        when (tag) {
            NotificationJob.TAG -> return NotificationJob()
            else -> return null
        }
    }
}