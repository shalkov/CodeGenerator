package ru.shalkoff.android.codegenerator.recipe

import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.shalkoff.android.codegenerator.common.Constants.DI
import ru.shalkoff.android.codegenerator.common.Constants.MODULE_KT
import ru.shalkoff.android.codegenerator.common.saveFile
import ru.shalkoff.android.codegenerator.templates.impl.getDiTemplate
import java.io.File

fun RecipeExecutor.createDi(
    implModulePath: String,
    classPrefix: String,
    packageName: String
) {
    val diContent = getDiTemplate(
        classPrefix = classPrefix,
        packageName = packageName
    )
    saveFile(
        absolutePath = "${implModulePath}${File.separator}$DI",
        relative = "${classPrefix}$MODULE_KT",
        content = diContent
    )
}