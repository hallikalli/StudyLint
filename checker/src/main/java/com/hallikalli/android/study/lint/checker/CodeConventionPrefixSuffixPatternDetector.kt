package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.openapi.util.NlsSafe
import java.util.*
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UVariable


@Suppress("UnstableApiUsage")
/**
 * list, array형식은 ~s혹은 ~es 등의 복수형으로 적어주세요
 */
class CodeConventionPrefixSuffixPatternDetector : Detector(), Detector.UastScanner {

    private val listTypes = listOf("java.util.List", "java.lang.Iterable", "java.util.ArrayList",
                                   "double[]", "float[]", "long[]", "int[]", "char[]", "short[]", "byte[]", "boolean[]")

    override fun getApplicableUastTypes() = listOf<Class<out UElement>>(UVariable::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitVariable(node: UVariable) {
            if (isArrayOrListReceiver(node.type.canonicalText) && isNotEndWithS(node.name)) {
                report(context, node, "복수형 ~s(ex)를 사용해주세요(ex.itemList->items")
            }
        }
    }

    private fun isNotEndWithS(name: String?) = name?.endsWith("s")?.not()?:false

    private fun isArrayOrListReceiver(canonicalText: @NlsSafe String): Boolean {
        var receiverName = canonicalText
        if (receiverName.contains("<")) // 제네릭 지우기용 ex. java.util.List<String> -> java.util.List
            receiverName = receiverName.substring(0, receiverName.indexOf("<"))
        return listTypes.contains(receiverName)
    }

    private fun report(context: JavaContext, node: UVariable, message: String) {
        val incident = Incident(context, NamingPatternViewHolderDetector.ISSUE)
            .message(message)
            .location(context.getNameLocation(node))
        context.report(incident)
    }

    companion object {
        const val message = "'@+id/jp_~'로 시작되어야 합니다."
        val ISSUE = Issue.create(
            id = CodeConventionPrefixSuffixPatternDetector::class.java.simpleName,
            briefDescription = """android:id 속성에 jp Prefix 누락 """,
            explanation = message,
            category = Category.CORRECTNESS,
            severity = Severity.WARNING,
            implementation = Implementation(
                CodeConventionPrefixSuffixPatternDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE),
            )
        )
    }
}