package ru.shalkoff.android.codegenerator.module

import com.android.tools.idea.npw.module.ConfigureModuleStep
import com.android.tools.idea.npw.toWizardFormFactor
import com.android.tools.idea.observable.core.BoolProperty
import com.android.tools.idea.observable.core.BoolValueProperty
import com.android.tools.idea.observable.expressions.Expression
import com.android.tools.idea.observable.ui.SelectedProperty
import com.android.tools.idea.observable.ui.TextProperty
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBUI.Borders.empty
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import ru.shalkoff.android.codegenerator.common.Constants.API_CONST
import ru.shalkoff.android.codegenerator.common.Constants.IMPL_CONST
import ru.shalkoff.android.codegenerator.common.UiUtils.contextLabel
import ru.shalkoff.android.codegenerator.common.bundle.AndroidCodeGeneratorBundle.message
import ru.shalkoff.android.codegenerator.common.convertColonsToDotsAndLowercase
import ru.shalkoff.android.codegenerator.common.validators.ClassPrefixValidator
import javax.swing.*

class AndroidConfigureModuleStep(
    private val model: AndroidModuleModel,
    minSdkLevel: Int,
    private val basePackage: String?,
    title: String
) : ConfigureModuleStep<AndroidModuleModel>(
    model,
    model.formFactor.get().toWizardFormFactor(),
    minSdkLevel,
    basePackage,
    title
) {

    private val classPrefixField = JTextField()
    private val packageNameImplField: JTextField = JBTextField().apply {
        isEditable = false
    }
    private val packageNameApiField: JTextField = JBTextField().apply {
        isEditable = false
    }
    private val generateUnitTestClassCheck = JBCheckBox(
        message("generator.module.checkbox.unit.viewmodel.text"),
        false
    )

    init {
        bindsAllElements()

        initPackageNameApiField()
        initPackageNameImplField()
        initClassPrefixFieldValidator()
    }

    override fun createMainPanel(): DialogPanel {
        return panel {
            row(
                contextLabel(
                    message("generator.module.class.prefix.title"),
                    message("generator.module.class.prefix.description")
                )
            ) {
                cell(classPrefixField).align(Align.FILL)
            }
            row(
                contextLabel(
                    message("generator.module.name.module.title"),
                    message("generator.module.name.module.description")
                )
            ) {
                cell(moduleName).align(AlignX.FILL)
            }
            row(message("generator.module.name.module.api.title")) {
                cell(packageNameApiField).align(AlignX.FILL)
            }
            row(message("generator.module.name.module.impl.title")) {
                cell(packageNameImplField).align(AlignX.FILL)
            }
            row {
                topGap(TopGap.SMALL)
                cell(generateUnitTestClassCheck)
            }
        }.withBorder(empty(6))
    }

    override fun onProceeding() {
        // вызывается, когда нажимаем Finish
    }

    override fun getPreferredFocusComponent(): JComponent = classPrefixField

    private fun initPackageNameApiField() {
        val isPackageNameApiSynced: BoolProperty = BoolValueProperty(true)
        val packageNameApiText = TextProperty(packageNameApiField)
        val computedPackageApiName: Expression<String> = object : Expression<String>(model.moduleName) {
            override fun get() = "${basePackage}.${convertColonsToDotsAndLowercase(model.moduleName.get())}.$API_CONST"
        }
        bindings.bind(packageNameApiText, computedPackageApiName, isPackageNameApiSynced)
        listeners.listen(packageNameApiText) { value: String -> isPackageNameApiSynced.set(value == computedPackageApiName.get()) }
    }

    private fun initPackageNameImplField() {
        val isPackageNameImplSynced: BoolProperty = BoolValueProperty(true)
        val packageNameImplText = TextProperty(packageNameImplField)
        val computedPackageImplName: Expression<String> = object : Expression<String>(model.moduleName) {
            override fun get() = "${basePackage}.${convertColonsToDotsAndLowercase(model.moduleName.get())}.$IMPL_CONST"
        }
        bindings.bind(packageNameImplText, computedPackageImplName, isPackageNameImplSynced)
        listeners.listen(packageNameImplText) { value: String -> isPackageNameImplSynced.set(value == computedPackageImplName.get()) }
    }

    private fun initClassPrefixFieldValidator() {
        //валидатор префикса класса
        val classPrefixText = TextProperty(classPrefixField)
        validatorPanel.registerValidator(classPrefixText, ClassPrefixValidator())
    }

    private fun bindsAllElements() {
        bindings.bind(
            model.isUnitTestClassGenerate,
            SelectedProperty(generateUnitTestClassCheck)
        )
        bindings.bind(
            model.classPrefix,
            TextProperty(classPrefixField)
        )
        bindings.bind(
            model.packageNameApi,
            TextProperty(packageNameApiField)
        )
        bindings.bind(
            model.packageNameImpl,
            TextProperty(packageNameImplField)
        )
        bindings.bind(
            model.customModuleName,
            TextProperty(moduleName)
        )
    }
}

