package name.mharbovskyi.findchargingstation.presentation.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import name.mharbovskyi.findchargingstation.data.di.RepositoryModule
import name.mharbovskyi.findchargingstation.domain.di.UsecaseModule
import name.mharbovskyi.findchargingstation.presentation.view.LoginActivity
import name.mharbovskyi.findchargingstation.presentation.view.MainActivity
import name.mharbovskyi.findchargingstation.presentation.view.SplashActivity

@Module(includes = [
    RepositoryModule::class,
    UsecaseModule::class
])
internal abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        FragmentsBuilder::class
    ])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity
}

