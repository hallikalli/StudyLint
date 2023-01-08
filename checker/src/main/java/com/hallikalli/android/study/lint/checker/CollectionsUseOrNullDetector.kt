package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import java.util.*
import org.jetbrains.uast.UCallExpression

@Suppress("UnstableApiUsage")
class CollectionsUseOrNullDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames() = listOf("first","last",
                                                     "max","maxBy","maxOf","maxOfWith","maxWith" , 
                                                     "min","minBy","minOf","minOfWith","minWith"
    )

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val methodName = node.methodName
        val incident = Incident(context, ISSUE)
            .message(node.methodName + message)
            .at(node.methodIdentifier!!)
            .fix(LintFix.create().replace().text(methodName).with(methodName + orNull).build())
        context.report(incident)
    }

    companion object {
        private const val orNull = "OrNull"
        private const val message = "$orNull()을 사용해주세요"

        @JvmField
        val ISSUE = Issue.create(
            id = CollectionsUseOrNullDetector::class.java.simpleName,
            briefDescription = "수정 필요",
            explanation = message,
            category = Category.CORRECTNESS,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(
                CollectionsUseOrNullDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE),
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}