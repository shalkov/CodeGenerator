package ru.shalkoff.android.codegenerator.module

import com.android.sdklib.SdkVersionInfo.LOWEST_ACTIVE_API
import com.android.tools.idea.npw.model.ProjectSyncInvoker
import com.android.tools.idea.npw.module.ModuleGalleryEntry
import com.android.tools.idea.wizard.model.SkippableWizardStep
import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.FormFactor
import com.intellij.openapi.project.Project
import ru.shalkoff.android.codegenerator.common.PluginIcons
import ru.shalkoff.android.codegenerator.common.ProjectUtils.getApplicationId
import ru.shalkoff.android.codegenerator.common.bundle.AndroidCodeGeneratorBundle.message
import javax.swing.Icon

class AndroidModuleGalleryEntry : ModuleGalleryEntry {

    override val name: String
        get() = message("generator.module.title")

    override val description: String
        get() = message("generator.module.description")

    override val icon: Icon
        get() = PluginIcons.ANDROID_ICON_16

    override fun createStep(
        project: Project,
        moduleParent: String,
        projectSyncInvoker: ProjectSyncInvoker
    ): SkippableWizardStep<*> {

        val basePackage = getApplicationId(project)

        val model = AndroidModuleModel.fromExistingProject(
            project,
            moduleParent,
            projectSyncInvoker,
            FormFactor.Mobile,
            Category.Other,
            isLibrary = false
        )
        return AndroidConfigureModuleStep(
            model,
            LOWEST_ACTIVE_API,
            basePackage,
            message("generator.module.step.first.name")
        )
    }
}
