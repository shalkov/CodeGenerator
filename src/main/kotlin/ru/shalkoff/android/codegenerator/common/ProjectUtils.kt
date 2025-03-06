package ru.shalkoff.android.codegenerator.common

import com.android.AndroidProjectTypes
import com.android.tools.idea.gradle.dsl.api.ProjectBuildModel
import com.android.tools.idea.project.AndroidProjectInfo
import com.intellij.openapi.project.Project
import ru.shalkoff.android.codegenerator.common.Constants.DEFAULT_BASE_PACKAGE

object ProjectUtils {

    /**
     * Получение applicationId у первого app модуля
     */
    fun getApplicationId(project: Project): String {
//        val appModules = AndroidProjectInfo.getInstance(project)
//            .getAllModulesOfProjectType(AndroidProjectTypes.PROJECT_TYPE_APP)
//        val module = appModules.firstOrNull()
//        val targetApplicationId = module?.run {
//            val projectBuildModel = ProjectBuildModel.getOrLog(project)
//            val targetModuleAndroidModel = projectBuildModel?.getModuleBuildModel(module)?.android()
//            targetModuleAndroidModel?.namespace()?.valueAsString()
//        }
//        return targetApplicationId ?: DEFAULT_BASE_PACKAGE
        return DEFAULT_BASE_PACKAGE
    }
}