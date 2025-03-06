package ru.shalkoff.android.codegenerator.common

import com.intellij.openapi.util.IconLoader

class PluginIcons {

    companion object {

        private const val RESOURCE_PATH = "/icons"

        val ANDROID_ICON_16 = IconLoader.getIcon("$RESOURCE_PATH/hacker.png", PluginIcons::class.java.classLoader)
    }
}
