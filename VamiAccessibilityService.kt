package com.vami.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.speech.tts.TextToSpeech
import java.util.*

class VamiAccessibilityService : AccessibilityService(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    override fun onServiceConnected() {
        super.onServiceConnected()
        tts = TextToSpeech(this, this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: return

        // Agar VAMI ke alawa koi aur app open ho
        if (packageName != "com.vami.app") {

            speak("Please stay focused. Coming back to VAMI.")

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
            startActivity(intent)
        }
    }

    override fun onInterrupt() {}

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.ENGLISH
        }
    }

    private fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
