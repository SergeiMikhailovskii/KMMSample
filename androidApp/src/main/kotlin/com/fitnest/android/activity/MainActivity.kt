package com.fitnest.android.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.fitnest.android.di.viewModelModule
import com.fitnest.android.base.FitnestApp
import com.fitnest.di.repositoryModule
import com.fitnest.di.serviceModule
import com.fitnest.di.cookieModule
import org.kodein.di.*


@ExperimentalAnimationApi
class MainActivity : ComponentActivity(), DIAware {

    override val diContext: DIContext<*> = diContext(this)

    override val di by DI.lazy {
        bind<Context>() with instance(this@MainActivity)
        import(viewModelModule)
        import(repositoryModule)
        import(serviceModule)
        import(cookieModule)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnestApp()
        }
    }

}