package com.example.tvpo3

import com.example.tvpo3.domain.AlarmController
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class AlarmSpecificationTest(
    private val triggerAtMillis: Long,
    private val checkedAtMillis: Long,
    private val expectedRinging: Boolean
) {
    @Test
    fun matchesSpecifiedExample(): Unit {
        val controller = AlarmController()
        controller.setAlarm(triggerAtMillis, 0L)

        val result = requireNotNull(controller.tick(checkedAtMillis))

        assertEquals(expectedRinging, result.ringing)
        assertEquals(!expectedRinging, result.enabled)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(
            name = "{index}: alarm={0}, now={1}, ringing={2}"
        )
        fun examples(): Collection<Array<Any>> {
            return listOf(
                arrayOf<Any>(2000L, 0L, false),
                arrayOf<Any>(2000L, 1999L, false),
                arrayOf<Any>(2000L, 2000L, true),
                arrayOf<Any>(2000L, 2001L, true),
                arrayOf<Any>(1L, 0L, false),
                arrayOf<Any>(1L, 1L, true)
            )
        }
    }
}