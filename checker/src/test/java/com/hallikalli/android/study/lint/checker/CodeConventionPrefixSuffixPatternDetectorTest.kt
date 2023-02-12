package com.hallikalli.android.study.lint.checker

import Stubs.CollectionStub
import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [CodeConventionPrefixSuffixPatternDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class CodeConventionPrefixSuffixPatternDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(CodeConventionPrefixSuffixPatternDetector.ISSUE)

    override fun getDetector(): Detector = CodeConventionPrefixSuffixPatternDetector()

    @Test
    fun expectPass() {
        lint()
            .allowMissingSdk(true)
            .files(CollectionStub.STUB_COLLECTIONS,
                   kotlin(
                """
class CodeConventionPrefix {
    var arrayInts = arrayListOf<Int>()
    var mutableInts = mutableListOf<Int>()
    var ints = listOf<Int>()
    var floats = floatArrayOf()
    var longs = longArrayOf()
    var ints2 = intArrayOf()
    var chars = charArrayOf()
    var shorts= shortArrayOf()
    var bytes = byteArrayOf()
    var booleans = booleanArrayOf()
}
""".trimIndent()
            ).indented()
            )
            .run()
            .expectClean()
    }

    @Test
    fun expectFail() {
        lint()
            .allowMissingSdk(true)
            .files(CollectionStub.STUB_COLLECTIONS,
                   kotlin(
                """
class CodeConventionPrefix {
    var arrayInts = arrayListOf<Int>()
    var mutableInts = mutableListOf<Int>()
    var floats = floatArrayOf()
    var longs = longArrayOf()
    var ints = intArrayOf()
    var chars = charArrayOf()
    var shorts= shortArrayOf()
    var bytes = byteArrayOf()
    var booleans = booleanArrayOf()
}
                    """
            )
            )
            .run()
            .expectWarningCount(10)
    }
}