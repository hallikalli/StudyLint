package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Incident
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import java.util.EnumSet
import org.jetbrains.uast.UClass

/**
 * ViewHolder 생성시 네이밍 규칙: Vh____
 */
class NamingPatternViewHolderDetector : Detector(), Detector.UastScanner, SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitClass(node: UClass) {
            val name = node.name ?: return
            if (name == "ViewHolder") return
            if ((name.contains("ViewHolder", true) || name.contains("VH", true))
                && !name.startsWith("Vh", false)
            ) {
                val incident = Incident(context, ISSUE)
                    .message(message)
                    .location(context.getNameLocation(node))
                context.report(incident)
            }
        }

    }

    companion object {
        private const val message = "Vh___를 사용해주세요"
        val ISSUE = Issue.create(
            id = NamingPatternViewHolderDetector::class.java.simpleName,
            briefDescription = "수정 필요",
            explanation = message,
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                NamingPatternViewHolderDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE),
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}