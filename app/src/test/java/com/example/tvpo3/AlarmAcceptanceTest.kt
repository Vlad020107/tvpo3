package com.example.tvpo3

import com.example.tvpo3.domain.AlarmController
import org.junit.Assert.*
import org.junit.Test

class AlarmAcceptanceTest {
    @Test
    fun userSetsAlarmAndStopsSignal(): Unit {
        val controller = AlarmController()
        controller.setAlarm(2000L, 1000L)

        assertFalse(requireNotNull(controller.tick(1999L)).ringing)
        assertTrue(requireNotNull(controller.tick(2000L)).ringing)

        val stopped = requireNotNull(controller.dismiss())

        assertFalse(stopped.ringing)
        assertFalse(stopped.enabled)
        assertFalse(requireNotNull(controller.tick(3000L)).ringing)
    }

    @Test
    fun changedTimeReplacesPreviousTrigger(): Unit {
        val controller = AlarmController()
        controller.setAlarm(2000L, 1000L)
        controller.changeTime(4000L, 1500L)

        assertFalse(requireNotNull(controller.tick(2000L)).ringing)
        assertTrue(requireNotNull(controller.tick(4000L)).ringing)
    }

    @Test
    fun stoppingInactiveSignalDoesNotCancelScheduledAlarm(): Unit {
        val controller = AlarmController()
        controller.setAlarm(2000L, 1000L)
        controller.dismiss()

        assertTrue(requireNotNull(controller.alarm).enabled)
        assertTrue(requireNotNull(controller.tick(2000L)).ringing)
    }
}