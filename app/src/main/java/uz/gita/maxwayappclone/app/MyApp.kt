package uz.gita.maxwayappclone.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber
import uz.gita.maxwayappclone.data.repository_impl.AuthRepositoryImpl
import uz.gita.maxwayappclone.data.repository_impl.StoryRepositoryImpl
import uz.gita.maxwayappclone.data.repository_impl.BranchRepositoryImpl
import uz.gita.maxwayappclone.data.repository_impl.NotificationRepositoryImpl
import uz.gita.maxwayappclone.data.repository_impl.SearchRepositoryImpl

class MyApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        Timber.plant(Timber.DebugTree())

        AuthRepositoryImpl.init()
        StoryRepositoryImpl.init()
        BranchRepositoryImpl.init()
        NotificationRepositoryImpl.init()
        SearchRepositoryImpl.init()
    }
}

