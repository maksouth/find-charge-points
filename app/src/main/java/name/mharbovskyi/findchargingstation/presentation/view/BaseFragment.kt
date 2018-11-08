package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import name.mharbovskyi.findchargingstation.presentation.viewmodel.BaseViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoadingState

abstract class BaseFragment: DaggerFragment() {

    private val TAG = BaseFragment::class.java.simpleName

    abstract val progressBar: View
    abstract val rootLayout: ViewGroup

    fun subscribe(viewModel: BaseViewModel) {
        viewModel.errors.observe(this, Observer{ resId -> resId?.let {
            Log.d(TAG, getString(resId))
            Snackbar.make(rootLayout, resId, Snackbar.LENGTH_LONG).show()
        }
        })

        viewModel.loading.observe(this, Observer {it?.let {
            Log.d(TAG, "Load state $it")
            when(it) {
                LoadingState.SHOW -> progressBar.visibility = View.VISIBLE
                LoadingState.HIDE -> progressBar.visibility = View.GONE
            }
        }
        })

        viewModel.info.observe(this, Observer {
            it?.let {
                Log.d(TAG, "Info ${getString(it)}")
                Snackbar.make(rootLayout, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

}