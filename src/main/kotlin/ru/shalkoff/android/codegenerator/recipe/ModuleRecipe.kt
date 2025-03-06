package ru.shalkoff.android.codegenerator.recipe

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.shalkoff.android.codegenerator.common.*
import ru.shalkoff.android.codegenerator.common.Constants.API_CONST
import ru.shalkoff.android.codegenerator.common.Constants.IMPL_CONST

fun RecipeExecutor.createModuleFiles(
    moduleData: ModuleTemplateData,
    moduleName: String,
    classPrefix: String,
    isNeedGenerateUnitTestClass: Boolean
) {
    val apiRootPath = getRootPath(API_CONST, moduleData, moduleName)
    val implRootPath = getRootPath(IMPL_CONST, moduleData, moduleName)

    val packageName = getPackageName(moduleData.packageName, moduleName)

    val implModulePath = getModulePath(IMPL_CONST, implRootPath, packageName)

    // добавление модулей в settings.gradle.kts
    this.addIncludeToSettings("$moduleName:$API_CONST")
    this.addIncludeToSettings("$moduleName:$IMPL_CONST")

    createBuildGradle(
        implRootPath = implRootPath,
        apiRootPath = apiRootPath,
        moduleName = moduleName,
        packageName = packageName,
        isNeedGenerateUnitTestClass = isNeedGenerateUnitTestClass
    )
    createDi(
        implModulePath = implModulePath,
        classPrefix = classPrefix,
        packageName = packageName,
    )
    createViewModel(
        implModulePath = implModulePath,
        classPrefix = classPrefix,
        packageName = packageName
    )
    createScreen(
        implModulePath = implModulePath,
        classPrefix = classPrefix,
        packageName = packageName
    )
    if (isNeedGenerateUnitTestClass) {
        //TODO добавить код
    }
}


