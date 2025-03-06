package ru.shalkoff.android.codegenerator.recipe

import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.shalkoff.android.codegenerator.common.Constants.PRESENTATION
import ru.shalkoff.android.codegenerator.common.Constants.VIEW_MODEL_KT
import ru.shalkoff.android.codegenerator.common.saveFile
import ru.shalkoff.android.codegenerator.templates.impl.getViewModelTemplate
import java.io.File

fun RecipeExecutor.createViewModel(
    implModulePath: String,
    classPrefix: String,
    packageName: String
) {
    val viewModelContent = getViewModelTemplate(
        classPrefix = classPrefix,
        packageName = packageName
    )
    saveFile(
        absolutePath = "${implModulePath}${File.separator}$PRESENTATION",
        relative = "${classPrefix}$VIEW_MODEL_KT",
        content = viewModelContent
    )
}