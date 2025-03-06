package ru.shalkoff.android.codegenerator.module

import com.android.tools.idea.npw.module.ModuleDescriptionProvider
import com.android.tools.idea.npw.module.ModuleGalleryEntry
import com.intellij.openapi.project.Project

class AndroidModuleDescriptionProvider : ModuleDescriptionProvider {

    override fun getDescriptions(project: Project): Collection<ModuleGalleryEntry> {
        return listOf(AndroidModuleGalleryEntry())
    }
}