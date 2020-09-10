package module

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

/**
 * desc
 * author: 朱子楚
 * time: 2020/9/10 1:44 PM
 * since: v 1.0.0
 */
class ModuleHandler : DefaultHandler() {

    private lateinit var modules: Modules

    /**
     * 开始解析文档
     */
    override fun startDocument() {
        super.startDocument()
        modules= Modules()
        Log.i("startDocument","开始解析xml文档")
    }

    /**
     * 结束解析文档
     */
    override fun endDocument() {
        super.endDocument()
        Log.i("endDocument","开始解析xml文档")
    }

    /**
     * 当解析到标签中的内容的时候调用
     */
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
    }

    /**
     * 每一个标签结束时调用
     */
    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        super.startElement(uri, localName, qName, attributes)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
    }

}