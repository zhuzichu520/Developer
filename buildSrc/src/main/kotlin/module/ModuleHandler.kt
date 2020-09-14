package module

import Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.File
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/10 1:44 PM
 * since: v 1.0.0
 */
class ModuleHandler : DefaultHandler() {

    lateinit var modules: Modules
    private lateinit var name: String
    private lateinit var application: String
    private var app: Boolean = false

    /**
     * 开始解析文档
     */
    override fun startDocument() {
        super.startDocument()
        Log.i("startDocument", "开始解析xml文档")
    }

    /**
     * 结束解析文档
     */
    override fun endDocument() {
        super.endDocument()
        Log.i("endDocument", "开始解析xml文档")
    }

    /**
     * 当解析到标签中的内容的时候调用
     */
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
    }

    /**
     * 每一个标签开始时调用
     */
    override fun startElement(
        uri: String?,
        localName: String?,
        name: String?,
        attributes: Attributes?
    ) {
        when (name) {
            "modules" -> {
                modules = Modules()
                attributes?.let {
                    for (index: Int in 0 until it.length) {
                        when (attributes.getQName(index)) {
                            "main" -> {

                            }
                        }
                    }
                }
            }
            "module" -> {
                attributes?.let {
                    for (index: Int in 0 until it.length) {
                        when (attributes.getQName(index)) {
                            "name" -> {
                                this.name = attributes.getValue(index)
                            }
                            "application" -> {
                                this.application = attributes.getValue(index)
                            }
                            "app" -> {
                                this.app = attributes.getValue(index) == "true"
                            }
                        }
                    }
                    this.modules.data.add(Module(this.name, this.application, this.app))
                }
            }
        }

    }

    /**
     * 每一个标签结束时调用
     */
    override fun endElement(uri: String?, localName: String?, name: String?) {

    }

}