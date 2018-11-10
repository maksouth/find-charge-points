package name.mharbovskyi.findchargingstation

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import name.mharbovskyi.findchargingstation.presentation.di.component.DaggerApplicationComponent

class PointsApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().create(this)
}