package ru.shalkoff.android.codegenerator.common.bundle

import java.io.InputStreamReader
import java.net.URL
import java.util.*

class UTF8Control : ResourceBundle.Control() {

    override fun newBundle(
        baseName: String,
        locale: Locale,
        format: String,
        loader: ClassLoader,
        reload: Boolean
    ): ResourceBundle? {
        val bundleName = toBundleName(baseName, locale)
        val resourceName = toResourceName(bundleName, "properties")
        var bundle: ResourceBundle? = null
        var stream: java.io.InputStream? = null

        try {
            // Открываем поток к ресурсному файлу
            stream = if (reload) {
                val url: URL? = loader.getResource(resourceName)
                url?.openConnection()?.apply { useCaches = false }?.getInputStream()
            } else {
                loader.getResourceAsStream(resourceName)
            }

            // Если поток найден, читаем его как UTF-8
            if (stream != null) {
                InputStreamReader(stream, Charsets.UTF_8).use { reader ->
                    bundle = PropertyResourceBundle(reader)
                }
            }
        } finally {
            stream?.close()
        }

        return bundle
    }
}
