package com.example.tvpo3

import com.example.tvpo3.domain.AlarmController
import org.junit.Assert.*
import org.junit.Test

class AlarmTddTest {
    @Test
    fun setsFutureAlarm(): Unit {
        val controller = AlarmController()
        val alarm = controller.setAlarm(2000L, 1000L)

        assertEquals(2000L, alarm.triggerAtMillis)
        assertTrue(alarm.enabled)
        assertFalse(alarm.ringing)
        assertEquals(alarm, controller.alarm)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsPastTime(): Unit {
        AlarmController().setAlarm(999L, 1000L)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsCurrentTime(): Unit {
        AlarmController().setAlarm(1000L, 1000L)
    }

    @Test(expected = IllegalStateException::class)
    fun rejectsChangingMissingAlarm(): Unit {
        AlarmController().changeTime(2000L, 1000L)
    }

    @Test
    fun invalidChangePreservesExistingAlarm(): Unit {
        val controller = AlarmController()
        val original = controller.setAlarm(2000L, 1000L)
        var rejected = false

        try {
            controller.changeTime(500L, 1000L)
        } catch (exception: IllegalArgumentException) {
            rejected = true
        }

        assertTrue(rejected)
        assertEquals(original, controller.alarm)
    }

    @Test
    fun missingAlarmOperationsReturnNull(): Unit {
        val controller = AlarmController()

        assertNull(controller.tick(1000L))
        assertNull(controller.dismiss())
    }
}