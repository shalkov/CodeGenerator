package ru.shalkoff.android.codegenerator.common

import com.intellij.ui.ContextHelpLabel
import javax.swing.JLabel
import javax.swing.SwingConstants

object UiUtils {

    fun contextLabel(text: String, contextHelpText: String): JLabel {
        return ContextHelpLabel.create(contextHelpText).apply {
            setText(text)
            horizontalTextPosition = SwingConstants.LEFT
        }
    }
}