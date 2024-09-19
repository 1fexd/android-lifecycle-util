package fe.android.lifecycle

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AppLifecycleObserver(
    private val owner: LifecycleOwner,
) : LifecycleServiceRegistry {

    override val lifecycleCoroutineScope: LifecycleCoroutineScope
        get() = owner.lifecycleScope

    companion object {
        fun observe(owner: LifecycleOwner): AppLifecycleObserver {
            return AppLifecycleObserver(owner).also {
                owner.lifecycle.addObserver(it)
            }
        }
    }

    private val services = mutableListOf<LifecycleAwareService>()

    override fun register(service: LifecycleAwareService) {
        services.add(service)
    }

    override fun unregister(service: LifecycleAwareService) {
        services.remove(service)
    }

    private fun notifyServices(owner: LifecycleOwner, notify: suspend (LifecycleAwareService) -> Unit) {
        owner.lifecycleScope.launch {
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
