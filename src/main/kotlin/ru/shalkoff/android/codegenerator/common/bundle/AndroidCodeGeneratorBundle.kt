package ru.shalkoff.android.codegenerator.common.bundle

import com.intellij.AbstractBundle
import org.jetbrains.annotations.PropertyKey
import java.util.*

private const val CODE_GENERATOR_BUNDLE_NAME = "messages.CodeGeneratorBundle"

object AndroidCodeGeneratorBundle : AbstractBundle(CODE_GENERATOR_BUNDLE_NAME) {

    private val bundle: ResourceBundle = ResourceBundle.getBundle(
        CODE_GENERATOR_BUNDLE_NAME,
        UTF8Control()
    )

    fun message(
        @PropertyKey(resourceBundle = CODE_GENERATOR_BUNDLE_NAME)
        key: String, vararg params: Any
    ): String {
        return bundle.getString(key).format(*params)
    }
}