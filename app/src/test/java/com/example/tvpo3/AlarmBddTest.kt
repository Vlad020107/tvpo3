package com.example.tvpo3

import com.example.tvpo3.domain.AlarmController
import org.junit.Assert.*
import org.junit.Test

class AlarmBddTest {
    @Test
    fun givenScheduledAlarm_whenTimeArrives_thenSignalIsActive(): Unit {
        val controller = AlarmController()
        controller.setAlarm(2000L, 1000L)

        val result = requireNotNull(controller.tick(2000L))

        assertTrue(result.ringing)
    }

    @Test
    fun givenActiveSignal_whenUserDismisses_thenSignalStops(): Unit {
        val controller = AlarmController()
        controller.setAlarm(2000L, 1000L)
        controller.tick(2000L)

        val result = requireNotNull(controller.dismiss())

        assertFalse(result.ringing)
        assertFalse(result.enabled)
    }

    @Test
    fun givenTriggeredAlarm_whenTimeAdvances_thenSignalRemainsActive(): Unit {
        val controller = AlarmController()
        controller.setAlarm(2000L, 1000L)
        controller.tick(2000L)

        assertTrue(requireNotNull(controller.tick(3000L)).ringing)
    }
}