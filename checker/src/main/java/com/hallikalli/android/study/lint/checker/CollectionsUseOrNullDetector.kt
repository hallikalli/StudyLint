package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.PsiMethod
import java.util.*
import org.jetbrains.kotlin.psi.KtArrayAccessExpression
import org.jetbrains.uast.UCallExpression

/**
 * get___()대신 getOrNull을 사용하도록 워닝표시
 */
@Suppress("UnstableApiUsage")
class CollectionsUseOrNullDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf(UCallExpression::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitCallExpression(node: UCallExpression) {
            if (!isReceiverContain(node.receiverType?.canonicalText ?: return)) return
            val methodName = node.methodName ?: return
            if (!isMethodContain(methodName)) return
            report(context, node)
        }
    }

    fun isReceiverContain(canonicalText: @NlsSafe String): Boolean {
        var receiverName = canonicalText
        if (receiverName.contains("<")) // 제네릭 지우기용 ex. java.util.List<String> -> java.util.List
            receiverName = receiverName.substring(0, receiverName.indexOf("<"))
        return receiverNames.contains(receiverName)
    }

    fun isMethodContain(methodName: String): Boolean {
        return methods.contains(methodName)
    }

    //FIXME : doubleArrayOf 같은 경우엔 잡히지않음
    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        //  KtArrayAccessExpression: []를 사용한 경우
        println("visitMethodCall ${node.methodName}")
        if (node.sourcePsi is KtArrayAccessExpression) {
            report(context, node, false)
        }
    }

    private fun report(context: JavaContext, node: UCallExpression, isEnableFix: Boolean = true) {
        val methodName = node.methodName
        val incident = Incident(context, ISSUE)
            .message(methodName + message)
            .at(node.methodIdentifier!!)
        if (isEnableFix) {
            incident.fix(LintFix.create().replace().text(methodName).with(methodName + orNull).build())
        }
        context.report(incident)
    }

    companion object {
        private const val orNull = "OrNull"
        private const val message = "$orNull()을 사용해주세요"
        private val receiverNames = listOf("java.util.List", "java.lang.Iterable", "java.util.ArrayList",
                                           "double[]", "float[]", "long[]", "int[]", "char[]", "short[]", "byte[]", "boolean[]")
        private val methods = listOf("get", "first", "last",
                                     "max", "maxBy", "maxOf", "maxOfWith", "maxWith",
                                     "min", "minBy", "minOf", "minOfWith", "minWith")

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