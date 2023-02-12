package com.hallikalli.android.study.lint.checker

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import java.util.*
import org.w3c.dom.Attr


@Suppress("UnstableApiUsage")
/**
 * XML내 'android:id' 값은 '@+id/jp_' 로 시작되어야 합니다.
 */
class ViewIdPatternXmlDetector : LayoutDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableAttributes(): Collection<String>? {
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val attributeName = attribute.name
        val attributeValue = attribute.nodeValue
        if (attributeName == "android:id" && !attributeValue.startsWith("@+id/jp_")) {
            context.report(
                issue = ISSUE,
                scope = attribute,
                location = context.getValueLocation(attribute),
                message = message
            )
        }
    }

    companion object {
        const val message = "'@+id/jp_~'로 시작되어야 합니다."
        val ISSUE = Issue.create(
            id = ViewIdPatternXmlDetector::class.java.simpleName,
            briefDescription = """ 코드 컨벤션 변경""",
            explanation = "코드 컨벤션 이슈",
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                ViewIdPatternXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}