package name.mharbovskyi.findchargingstation.presentation.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import name.mharbovskyi.findchargingstation.PointsApplication
import name.mharbovskyi.findchargingstation.data.di.NetworkModule
import name.mharbovskyi.findchargingstation.presentation.di.module.ActivityBuilder
import name.mharbovskyi.findchargingstation.presentation.di.module.ApplicationModule

@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    NetworkModule::class,
    ActivityBuilder::class
])
interface ApplicationComponent: AndroidInjector<PointsApplication> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<PointsApplication>()
}