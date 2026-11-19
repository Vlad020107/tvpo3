package com.example.tvpo3.domain

data class Alarm(
    val triggerAtMillis: Long,
    val enabled: Boolean = true,
    val ringing: Boolean = false
)