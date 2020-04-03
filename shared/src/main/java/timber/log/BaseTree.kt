package timber.log

import com.hiwitech.android.shared.log.lumberjack.data.StackData

abstract class BaseTree : Timber.Tree() {

    companion object {
        internal val CALL_STACK_INDEX = 6
    }

    private val callStackCorrection = ThreadLocal<Int>()

    protected lateinit var lastStackData: StackData

    fun getCallStackCorrection(): Int? {
        val correction = callStackCorrection.get()
        if (correction != null) {
            callStackCorrection.remove()
        }
        return correction
    }

    fun setCallStackCorrection(value: Int) {
        callStackCorrection.set(value)
    }

    internal override fun getTag(): String? {

        // 1) get stack data
        var callStackCorrection = getCallStackCorrection() ?: 0
        lastStackData = StackData.create(CALL_STACK_INDEX + callStackCorrection)

        // 2) get custom tag if one exists
        val customTag = super.getTag()

        // 3) create a custom tag for the logs
        return if (customTag != null) {
            "[<$customTag> ${lastStackData.getStackTag()}]"
        } else {
            "[${lastStackData.getStackTag()}]"
        }
    }

    protected fun formatLine(tag: String?, message: String) = "[$tag]: $message"
}