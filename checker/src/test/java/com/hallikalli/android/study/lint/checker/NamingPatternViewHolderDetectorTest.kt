package com.hallikalli.android.study.lint.checker

import Stubs.Stubs.STUB_RECYCLE
import Stubs.Stubs.STUB_VIEW
import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestMode
import com.android.tools.lint.detector.api.*
import java.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [ReferenceViewIdXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class NamingPatternViewHolderDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(NamingPatternViewHolderDetector.ISSUE)

    override fun getDetector(): Detector = NamingPatternViewHolderDetector()

    @Test
    fun expectPass() {
        lint()
            .allowMissingSdk(true)
            .files(STUB_VIEW, STUB_RECYCLE,
                   kotlin(
                       """
                           package com.hallikalli.android.study.lint
                            import android.view.View
                            import androidx.recyclerview.widget.RecyclerView
                        class Test {
                        class VhTest(itemView: View) : RecyclerView.ViewHolder(itemView) 
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
            .files(STUB_VIEW, STUB_RECYCLE,
                   kotlin(
                       """
                        package com.hallikalli.android.study.lint

                        import android.view.View
                        import androidx.recyclerview.widget.RecyclerView
                        class Test {
                        class TestViewHolderTest(itemView: View) : RecyclerView.ViewHolder(itemView)
                        class ViewHolderTest(itemView: View) : RecyclerView.ViewHolder(itemView)
                        class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
                        class TestVh4(itemView: View) : RecyclerView.ViewHolder(itemView)
                        class TestVH5(itemView: View) : RecyclerView.ViewHolder(itemView) 
                        }
                    """
                   )
            )
            .run()
            .expectWarningCount(5)
    }
}