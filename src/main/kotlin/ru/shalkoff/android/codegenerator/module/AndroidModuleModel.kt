package ru.shalkoff.android.codegenerator.module

import com.android.tools.idea.npw.project.GradleAndroidModuleTemplate.createDefaultModuleTemplate
import com.android.tools.idea.npw.model.ExistingProjectModelData
import com.android.tools.idea.npw.model.ProjectModelData
import com.android.tools.idea.npw.model.ProjectSyncInvoker
import com.android.tools.idea.npw.module.ModuleModel
import com.android.tools.idea.npw.template.ModuleTemplateDataBuilder
import com.android.tools.idea.observable.core.BoolValueProperty
import com.android.tools.idea.observable.core.ObjectProperty
import com.android.tools.idea.observable.core.ObjectValueProperty
import com.android.tools.idea.observable.core.StringValueProperty
import com.android.tools.idea.projectsystem.NamedModuleTemplate
import com.android.tools.idea.templates.recipe.DefaultRecipeExecutor
import com.android.tools.idea.wizard.template.*
import com.google.wireless.android.sdk.stats.AndroidStudioEvent.TemplateRenderer
import com.google.wireless.android.sdk.stats.AndroidStudioEvent.TemplatesUsage.TemplateComponent.WizardUiContext
import com.google.wireless.android.sdk.stats.AndroidStudioEvent.TemplatesUsage.TemplateComponent.WizardUiContext.NEW_MODULE
import com.intellij.openapi.project.Project
import ru.shalkoff.android.codegenerator.common.bundle.AndroidCodeGeneratorBundle.message
import ru.shalkoff.android.codegenerator.recipe.createModuleFiles

class AndroidModuleModel(
    projectModelData: ProjectModelData,
    template: NamedModuleTemplate,
    moduleParent: String,
    override val formFactor: ObjectProperty<FormFactor>,
    override val category: ObjectProperty<Category>,
    override val isLibrary: Boolean = false,
    wizardContext: WizardUiContext
) : ModuleModel(
    "",
    "",
    isLibrary,
    projectModelData,
    template,
    moduleParent,
    wizardContext
) {
    val customModuleName = StringValueProperty()
    val classPrefix = StringValueProperty()
    val packageNameImpl = StringValueProperty()
    val packageNameApi = StringValueProperty()
    val isUnitTestClassGenerate = BoolValueProperty()

    override val moduleTemplateDataBuilder = ModuleTemplateDataBuilder(
        projectTemplateDataBuilder = projectTemplateDataBuilder,
        isNewModule = false,
        viewBindingSupport = viewBindingSupport.getValueOr(ViewBindingSupport.SUPPORTED_4_0_MORE)
    )

    override val renderer = ModuleTemplateRenderer()

    init {
        if (applicationName.isEmpty.get()) {
            applicationName.set(message("generator.module.application.name"))
        }
    }

    inner class ModuleTemplateRenderer : ModuleModel.ModuleTemplateRenderer() {

        override val recipe: Recipe
            get() = { data: TemplateData ->
                if (this is DefaultRecipeExecutor) {
                    createModuleFiles(
                        moduleData = data as ModuleTemplateData,
                        classPrefix = classPrefix.get(),
                        moduleName = customModuleName.get(),
                        isNeedGenerateUnitTestClass = isUnitTestClassGenerate.get()
                    )
                } else {
                    // Анализ — ничего не делаем или логируем для диагностики
                    println("Analyzing references, skipping actual file generation.")
                }
            }
    }

    override val loggingEvent: TemplateRenderer
        get() = TemplateRenderer.ANDROID_MODULE

    companion object {
        fun fromExistingProject(
            project: Project,
            moduleParent: String,
            projectSyncInvoker: ProjectSyncInvoker,
            formFactor: FormFactor,
            category: Category,
            isLibrary: Boolean = false
        ): AndroidModuleModel {
            return AndroidModuleModel(
                projectModelData = ExistingProjectModelData(project, projectSyncInvoker),
                template = createDefaultModuleTemplate(project, moduleParent),
                moduleParent = moduleParent,
                formFactor = ObjectValueProperty(formFactor),
                category = ObjectValueProperty(category),
                isLibrary = isLibrary,
                wizardContext = NEW_MODULE
            )
        }
    }
}