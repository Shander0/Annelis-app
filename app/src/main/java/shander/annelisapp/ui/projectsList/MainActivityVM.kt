package shander.annelisapp.ui.projectsList

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class MainActivityVM: ViewModel() {
    private lateinit var subscription: CompositeDisposable

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}