package ru.shalkoff.android.codegenerator.common.validators

import com.android.tools.adtui.validation.Validator
import com.android.tools.adtui.validation.Validator.Severity
import org.jetbrains.android.util.AndroidUtils
import ru.shalkoff.android.codegenerator.common.bundle.AndroidCodeGeneratorBundle.message

class ClassPrefixValidator : Validator<String> {

    override fun validate(value: String): Validator.Result {
        if (value.isBlank()) {
            return Validator.Result(
                Severity.ERROR,
                message("generator.module.validator.class.prefix.empty")
            )
        }
        if (value.isNotEmpty() && !value[0].isUpperCase()) {
            return Validator.Result(
                Severity.ERROR,
                message("generator.module.validator.class.prefix.start.uppercase")
            )
        }
        if (value.any { it !in 'A'..'Z' && it !in 'a'..'z' && it !in '0'..'9' && it != '_' && it != '$' }) {
            return Validator.Result(
                Severity.ERROR,
                message("generator.module.validator.class.prefix.special.cyrillic")
            )
        }
        if (!AndroidUtils.isIdentifier(value)) {
            return Validator.Result(
                Severity.ERROR,
                message("generator.module.validator.class.prefix.other")
            )
        }
        return Validator.Result.OK
    }
}