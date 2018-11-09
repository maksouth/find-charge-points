package name.mharbovskyi.findchargingstation.presentation.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import name.mharbovskyi.findchargingstation.presentation.viewmodel.BaseViewModel
import name.mharbovskyi.findchargingstation.presentation.viewmodel.LoadingState

var _counter = 0

abstract class BaseFragment: DaggerFragment() {

    val counter = _counter++

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onStart() {
        super.onStart()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(this::class.java.simpleName, "at $counter ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this::class.java.simpleName, "at ${object{}.javaClass.enclosingMethod.name}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(this::class.java.simpleName, "at ${object{}.javaClass.enclosingMethod.name}")
    }

}