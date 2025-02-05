package com.example.di

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton




class MainActivity : AppCompatActivity() {

       @Inject
       lateinit var sampleData: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

          val app = application as? MyApplication
            app?.appComponent?.inject(this) ?: throw IllegalStateException("Application instance is null")
            findViewById<TextView>(R.id.textView).text = sampleData
    }
}


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSampleData(): String {
        return "Hello from Dagger 2!"
    }
}


class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}