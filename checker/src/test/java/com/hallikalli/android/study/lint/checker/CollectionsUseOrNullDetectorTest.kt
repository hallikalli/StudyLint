package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import java.io.File
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [CollectionsUseOrNullDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class CollectionsUseOrNullDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(CollectionsUseOrNullDetector.ISSUE)

    override fun getDetector(): Detector = CollectionsUseOrNullDetector()

    @Test
    fun expectPass() {
        lint()
            .allowMissingSdk(true)
            .files(
                kotlin(
                    """
                        class Test {
                            fun test(){
                                val list = listOf<Int>()
                                list.firstOrNull()
                                list.lastOrNull()
                                list.maxOrNull()
                                list.maxByOrNull { it }
                                list.maxOfOrNull { it }
                                list.maxOfWithOrNull(compareBy { it }) { it }
                                list.maxWithOrNull(compareBy { it })
                                list.minOrNull()
                                list.minByOrNull { it }
                                list.minOfOrNull { it }
                                list.minOfWithOrNull(compareBy { it }) { it }
                                list.minWithOrNull(compareBy { it })
                            }
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
            .files(
                STUB_COLLECTIONS,
                   kotlin(
                       """
                        package com.hallikalli.android.study.lint

                        import kotlin.collections.*
                        import kotlin.collections.max 

                        class Test {
                            fun test(){
                                val list = listOf<Int>()
                                list.first()
                                list.last()
                                list.max()
                                list.maxBy { it }
                                list.maxOf { it }
                                list.maxOfWith(compareBy { it }) { it }
                                list.maxWith(compareBy { it })
                                list.min()
                                list.minBy { it }
                                list.minOf { it }
                                list.minOfWith(compareBy { it }) { it }
                                list.minWith(compareBy { it })
                            }
                        }
                    """.trimIndent()
                   ).indented()
            ).sdkHome(File("/Users/jadelee/Library/Android/sdk"))
            .run()
            .expectWarningCount(12)
    }
}

private val STUB_COLLECTIONS = LintDetectorTest.kotlin(
    """
    package kotlin.collections
    
    public fun <T : Comparable<T>> Iterable<T>.max(): T? {
        return maxOrNull()
    }
    inline fun <T, R : Comparable<R>> Iterable<T>.maxBy(selector: (T) -> R): T? {
        return maxByOrNull(selector)
    }
    fun <T> Iterable<T>.maxWith(comparator: Comparator<in T>): T? {
        return maxWithOrNull(comparator)
    }
    fun <T : Comparable<T>> Iterable<T>.min(): T? {
        return minOrNull()
    }
    inline fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T? {
        return minByOrNull(selector)
    }
    fun <T> Iterable<T>.minWith(comparator: Comparator<in T>): T? {
        return minWithOrNull(comparator)
    }
     """.trimIndent()
).indented()

