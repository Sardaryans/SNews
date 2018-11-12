package com.solo.snews.servieces

import com.evernote.android.job.JobRequest
import com.evernote.android.job.Job
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.NotificationCompat
import android.content.Intent
import android.app.PendingIntent
import com.solo.snews.R
import com.solo.snews.activities.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit


class NotificationJob : Job() {

    override fun onRunJob(params: Params): Result {
        val pi = PendingIntent.getActivity(context, 0,
                Intent(context, MainActivity::class.java), 0)

        val notification = NotificationCompat.Builder(context)
                .setContentTitle("SNews")
                .setContentText("Don't miss to check the latest news.")
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.solo_icon)
                .setShowWhen(true)
                .setLocalOnly(true)
                .build()

        NotificationManagerCompat.from(context)
                .notify(Random().nextInt(), notification)
        return Result.SUCCESS
    }

    companion object {

        val TAG = "job_demo_tag"

        fun scheduleJob() {
            JobRequest.Builder(TAG)
                    .setPeriodic(TimeUnit.MINUTES.toMillis(60),TimeUnit.MINUTES.toMillis(30) )
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }
    }
}