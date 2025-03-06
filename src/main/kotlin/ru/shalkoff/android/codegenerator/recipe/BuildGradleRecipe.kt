package ru.shalkoff.android.codegenerator.recipe

import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.shalkoff.android.codegenerator.common.Constants.BUILD_GRADLE_KTS
import ru.shalkoff.android.codegenerator.common.saveFile
import ru.shalkoff.android.codegenerator.templates.api.getApiBuildGradleTemplate
import ru.shalkoff.android.codegenerator.templates.impl.getBuildGradleTemplate

fun RecipeExecutor.createBuildGradle(
    implRootPath: String,
    apiRootPath: String,
    moduleName: String,
    packageName: String,
    isNeedGenerateUnitTestClass: Boolean
) {
    saveFile(
        absolutePath = implRootPath,
        relative = BUILD_GRADLE_KTS,
        content = getBuildGradleTemplate(
            moduleName = moduleName,
            packageName = packageName,
            isNeedGenerateUnitTestClass = isNeedGenerateUnitTestClass
        )
    )

    saveFile(
        absolutePath = apiRootPath,
        relative = BUILD_GRADLE_KTS,
        content = getApiBuildGradleTemplate(
            packageName = packageName
        )
    )
}