package org.mishankov.pega.kafka.serde

import com.pega.pegarules.pub.clipboard.ClipboardPage
import com.pega.pegarules.pub.runtime.PublicAPI
import com.pega.platform.kafka.serde.PegaSerde

/**
 * Sample Kafka custom keys SerDe implementation
 * Takes value of the property and puts its value into Kafka message
 */
class SimpleSerdeFromProperty : PegaSerde {
    private var propertyName: String? = null
    private var pageClass: String? = null

    override fun configure(tools: PublicAPI, configs: Map<String?, *>) {
        propertyName = configs["propertyName"].toString()
        pageClass = configs["pageClass"].toString()
    }

    override fun serialize(tools: PublicAPI, clipboardPage: ClipboardPage): ByteArray {
        val value: String = clipboardPage.getString(propertyName)
        return value.toByteArray()
    }

    override fun deserialize(tools: PublicAPI, data: ByteArray): ClipboardPage {
        val clipboardPage: ClipboardPage = tools.createPage(pageClass, "")
        clipboardPage.putString(propertyName, data.toString())

        return clipboardPage
    }
}
