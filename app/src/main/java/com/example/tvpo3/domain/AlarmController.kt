package com.example.tvpo3.domain

class AlarmController {
    var alarm: Alarm? = null
        private set

    fun setAlarm(triggerAtMillis: Long, nowMillis: Long): Alarm {
        require(triggerAtMillis > nowMillis) {
            "Alarm time must be in the future"
        }
        val result = Alarm(triggerAtMillis = triggerAtMillis)
        alarm = result
        return result
    }

    fun changeTime(triggerAtMillis: Long, nowMillis: Long): Alarm {
        checkNotNull(alarm) {
            "Alarm has not been set"
        }
        return setAlarm(triggerAtMillis, nowMillis)
    }

    fun tick(nowMillis: Long): Alarm? {
        val current = alarm ?: return null
        if (current.enabled && nowMillis >= current.triggerAtMillis) {
            alarm = current.copy(enabled = false, ringing = true)
        }
        return alarm
    }
//
    fun dismiss(): Alarm? {
        val current = alarm ?: return null
        if (current.ringing) {
            alarm = current.copy(ringing = false)
        }
        return alarm
    }
}