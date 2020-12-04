package com.chuzi.android.widget.log.lumberjack.data

import java.text.SimpleDateFormat
import java.util.*


class TimerData {

    companion object {
        val TIME_FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    }

    private var mStart: Long = 0
    private var mEnd: Long = 0
    private var mLaps: MutableList<Long>? = null

    fun getStart(): Long? = if (wasStarted()) mStart else null

    fun getEnd(): Long? = if (wasEnded()) null else mEnd

    fun getLaps(): List<Long>? = mLaps

    fun getLastLapTotal(): Long? = if (wasStarted() || mLaps == null) null else mLaps!![mLaps!!.size - 1] - mStart

    fun getTotal(): Long? = if (wasEnded()) mEnd - mStart else (if (wasStarted()) System.currentTimeMillis() - mStart else null)

    fun getStartTime() : String = if (wasStarted()) TIME_FORMATTER.format(mStart) else "NOT STARTED"

    fun wasStarted() = mStart != 0L

    fun isRunning() = mStart != 0L && mEnd == 0L

    fun wasEnded() = wasStarted() && mEnd != 0L

    fun start(): TimerData {
        if (!isRunning())
            mStart = System.currentTimeMillis()
        return this
    }

    fun stop(): Long? {
        if (!isRunning() || wasEnded())
            return null

        mEnd = System.currentTimeMillis()
        return mEnd - mStart
    }

    fun lap(): Long? {
        if (!isRunning())
            return null

        if (mLaps == null)
            mLaps = ArrayList()

        val lap = System.currentTimeMillis()
        mLaps!!.add(lap)
        return lap - if (mLaps!!.size == 1) mStart else mLaps!![mLaps!!.size - 2]
    }
}
