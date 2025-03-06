package ru.shalkoff.android.codegenerator.common

import com.android.tools.idea.wizard.template.ModuleTemplateData
import java.io.File

fun String.appendIf(condition: Boolean): String = takeIf {
    condition
}.orEmpty()


fun convertColonsToDotsAndLowercase(input: String): String {
    return input
        .replace("-", "_")
        .split(":")
        .filter { it.isNotEmpty() }
        .joinToString(".")
        .lowercase()
}

/**
 * Формирования рутового пути до модуля (корневая папка)
 *
 * Например: /Users/username/StudioProjects/project-android/profile/api
 */
fun getRootPath(
    lastSegmentPath: String,
    moduleData: ModuleTemplateData,
    moduleName: String
): String {
    val projectAbsolutePath = moduleData.projectTemplateData.rootDir.absolutePath
    val combinePath = buildPathFromColonSeparatedInput(
        projectAbsolutePath,
        moduleName
    )
    return "${combinePath}${File.separator}$lastSegmentPath"
}

/**
 * Формирования полного пути до модуля (самая внутренняя папка)
 *
 * Например: /Users/username/StudioProjects/project-android/profile/api/src/main/kotlin/ru/simple/project/profile/api
 */
fun getModulePath(
    lastSegmentPath: String,
    rootPath: String,
    packageName: String,
    isPathForUnitTest: Boolean = false
): String {
    val packagePath = packageName.replace(".", File.separator)
    val middlePath = if (isPathForUnitTest) {
        "test"
    } else {
        "main"
    }
    return "$rootPath${File.separator}" +
            "src${File.separator}" +
            "$middlePath${File.separator}" +
            "kotlin${File.separator}" +
            "$packagePath${File.separator}" +
            lastSegmentPath
}

fun getModuleTestPath(
    lastSegmentPath: String,
    rootPath: String,
    packageName: String,
): String {
    return getModulePath(
        lastSegmentPath,
        rootPath,
        packageName,
        true
    )
}

/**
 * Формирует модифицированный packageName
 * на основе оригинального packageName и moduleName
 *
 * Например packageName: ru.project.android.profile
 * moduleName: :analytics:profile
 * То на выходе получим: ru.project.android.analytics.profile
 */
fun getPackageName(
    originalPackageName: String,
    moduleName: String
): String {
    // Заменяем тире на нижнее подчёркивание в moduleName
    val normalizedModuleName = moduleName.replace("-", "_")

    // Разделяем входную строку по ":" и удаляем пустые элементы
    val parts = normalizedModuleName.split(":")
        .filter { it.isNotEmpty() }

    // Разделяем базовую строку по "."
    val baseParts = originalPackageName.split(".")

    // Заменяем последний элемент базовой строки на обогащенные данные
    val result = baseParts.dropLast(1) + parts

    // Объединяем результат через точку
    return result.joinToString(".")
}

/**
 * Формирует префиксное название для Route
 * Например, на входе: Profile_User__Test
 * На выходе: PROFILE_USER_TEST
 */
fun getFormattedScreenRouteName(classPrefix: String): String {
    return toUpperCaseWithSeparator(classPrefix)
}

/**
 * Добавляет двоеточие в начале, если его нет
 */
fun ensureColonPrefix(input: String): String {
    return if (input.startsWith(":")) {
        input
    } else {
        ":$input"
    }
}

/**
 * Делает первый символ строки в нижнем регистре
 */
fun lowercaseFirstLetter(input: String): String {
    if (input.isEmpty()) {
        return input
    }
    return input.replaceFirstChar { it.lowercase() }
}

/**
 * преобразует входную строку в верхний регистр и вставляет подчеркивание
 * (_) перед каждой заглавной буквой, если её нет в начале.
 */
private fun toUpperCaseWithSeparator(input: String): String {
    // Удаляем все существующие "_"
    val cleanedInput = input.replace("_", "")

    // Разделяем строку по заглавным буквам и добавляем разделитель "_"
    val regex = "(?<=.)(?=\\p{Upper})".toRegex()
    val result = cleanedInput.replace(regex, "_")

    // Преобразуем результат в верхний регистр
    return result.uppercase()
}

/**
 * Формирует путь на основе пути и имени модуля
 * Например: если путь такой: /Users/username/StudioProjects/project-android
 * А имя модуля такое: :profile:user
 * То на выходе будет такое: /Users/username/StudioProjects/project-android/profile/user
 */
private fun buildPathFromColonSeparatedInput(
    basePath: String,
    moduleName: String
): String {
    // Разделяем входную строку по ":" и удаляем пустые элементы
    val parts = moduleName.split(":")
        .filter { it.isNotEmpty() }

    // Объединяем фиксированную часть пути с элементами из input через File.separator
    return "$basePath${File.separator}${parts.joinToString(File.separator)}"
}