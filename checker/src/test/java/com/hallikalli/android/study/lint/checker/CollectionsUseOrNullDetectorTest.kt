package com.hallikalli.android.study.lint.checker

import Stubs.CollectionStub
import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
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
                                arrayListOf<Int>().getOrNull(0)
                                doubleArrayOf().getOrNull(0)
                                floatArrayOf().getOrNull(0)
                                longArrayOf().getOrNull(0)
                                intArrayOf().getOrNull(0)
                                charArrayOf().getOrNull(0)
                                shortArrayOf().getOrNull(0)
                                byteArrayOf().getOrNull(0)
                                booleanArrayOf().getOrNull(0)
                                val list = listOf<Int>()
                                list.getOrNull(0)
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
            .allowMissingSdk(true)
            .files(
                CollectionStub.STUB_COLLECTIONS,
                kotlin(
                    """
                        class Test {
                            fun test(){
                                doubleArrayOf()[1]
                                floatArrayOf()[3]
                                longArrayOf()[4]
                                intArrayOf()[5]
                                charArrayOf()[6]
                                shortArrayOf()[7]
                                byteArrayOf()[8]
                                booleanArrayOf()[9]
//                                val list = listOf<Int>()
//                                list.get(1)
//                                list[0]
//                                list.first()
//                                list.last()
//                                list.max()
//                                list.maxBy { it }
//                                list.maxOf { it }
//                                list.maxOfWith(compareBy { it }) { it }
//                                list.maxWith(compareBy { it })
//                                list.min()
//                                list.minBy { it }
//                                list.minOf { it }
//                                list.minOfWith(compareBy { it }) { it }
//                                list.minWith(compareBy { it })
                            }
                        }
                    """.trimIndent()
                ).indented()
            )
            .run()
            .expectWarningCount(22)
    }
}

