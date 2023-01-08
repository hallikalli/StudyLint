package com.hallikalli.android.study.lint.checker

import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import java.util.*
import org.w3c.dom.Attr


@Suppress("UnstableApiUsage")
/**
 * XML내 '@+id' 는 'android:id' 에서만 사용합니다.
 */
class ReferenceViewIdXmlDetector : LayoutDetector() {

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

    override fun getApplicableAttributes(): Collection<String>? {
        return XmlScannerConstants.ALL
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val attributeName = attribute.name
        val attributeValue = attribute.nodeValue
        if (attributeName != "android:id" && attributeValue.startsWith("@+id")) {
            context.report(
                issue = ISSUE,
                scope = attribute,
                location = context.getValueLocation(attribute),
                message = message,
                quickfixData = LintFix.create().replace().text("@+id").with("@id").build()
            )
        }
    }

    companion object {
        const val message = "'@id'를 이용해주세요"
        val ISSUE = Issue.create(
            id = ReferenceViewIdXmlDetector::class.java.simpleName,
            briefDescription = """android:id 외의 속성에 '@+id/~' 사용""",
            explanation = message,
            category = Category.CORRECTNESS,
            severity = Severity.ERROR,
            implementation = Implementation(
                ReferenceViewIdXmlDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }
}