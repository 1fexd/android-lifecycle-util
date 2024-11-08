package fe.android.lifecycle

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import fe.android.lifecycle.AppLifecycleObserver.Companion.TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

inline fun AppLifecycleObserver(
    owner: LifecycleOwner,
    crossinline handler: (CoroutineContext, Throwable) -> Unit = { _, throwable ->
        Log.e(TAG, "Exception ${throwable.message}", throwable)
    },
): AppLifecycleObserver {
    val observer = AppLifecycleObserver(owner, CoroutineExceptionHandler(handler))
    owner.lifecycle.addObserver(observer)

    return observer
}

class AppLifecycleObserver(
    private val owner: LifecycleOwner,
    private val exceptionHandler: CoroutineExceptionHandler,
) : LifecycleServiceRegistry {

    override val lifecycleCoroutineScope: LifecycleCoroutineScope
        get() = owner.lifecycleScope

    companion object {
        const val TAG = "AppLifecycleObserver"
    }

    private val services = mutableListOf<LifecycleAwareService>()

    override fun register(service: LifecycleAwareService) {
        services.add(service)
    }

    override fun unregister(service: LifecycleAwareService) {
        services.remove(service)
    }

    private fun notifyServices(owner: LifecycleOwner, notify: suspend (LifecycleAwareService) -> Unit) {
        owner.lifecycleScope.launch(exceptionHandler) {
            services.forEach { notify(it) }
        }
    }

    override fun onAppInitialized() {
        notifyServices(owner) { it.onAppInitialized(owner) }
    }

    override fun onCreate(owner: LifecycleOwner) {
        notifyServices(owner) { it.onCreate() }
    }

    override fun onResume(owner: LifecycleOwner) {
        notifyServices(owner) { it.onResume() }
    }

    override fun onPause(owner: LifecycleOwner) {
        notifyServices(owner) { it.onPause() }
    }

    override fun onStop(owner: LifecycleOwner) {
        notifyServices(owner) { it.onStop() }
    }
}
