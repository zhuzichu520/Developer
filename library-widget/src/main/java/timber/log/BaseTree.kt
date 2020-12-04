package timber.log

import com.chuzi.android.widget.log.lumberjack.data.StackData


abstract class BaseTree : Timber.Tree() {

    companion object {
        internal const val CALL_STACK_INDEX = 6
    }

    private val callStackCorrection = ThreadLocal<Int>()

    protected lateinit var lastStackData: StackData

    private fun getCallStackCorrection(): Int? {
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
        val callStackCorrection = getCallStackCorrection() ?: 0
        lastStackData = StackData.create(CALL_STACK_INDEX + callStackCorrection)
        val customTag = super.getTag()
        return if (customTag != null) {
            "[<$customTag> ${lastStackData.getStackTag()}]"
        } else {
            "[${lastStackData.getStackTag()}]"
        }
    }

    protected fun formatLine(tag: String?, message: String) = "[$tag]: $message"
}