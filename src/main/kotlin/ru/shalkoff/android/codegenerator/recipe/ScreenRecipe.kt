package ru.shalkoff.android.codegenerator.recipe

import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.shalkoff.android.codegenerator.common.Constants.PRESENTATION
import ru.shalkoff.android.codegenerator.common.Constants.SCREEN_KT
import ru.shalkoff.android.codegenerator.common.saveFile
import ru.shalkoff.android.codegenerator.templates.impl.getScreenTemplate
import java.io.File

fun RecipeExecutor.createScreen(
    implModulePath: String,
    classPrefix: String,
    packageName: String
) {
    val screenContent = getScreenTemplate(
        classPrefix = classPrefix,
        packageName = packageName
    )
    saveFile(
        absolutePath = "${implModulePath}${File.separator}$PRESENTATION",
        relative = "${classPrefix}$SCREEN_KT",
        content = screenContent
    )
}