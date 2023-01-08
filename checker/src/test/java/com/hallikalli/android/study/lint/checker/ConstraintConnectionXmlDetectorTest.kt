package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.*
import java.util.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests for the [ReferenceViewIdXmlDetector] custom lint check.
 */
@RunWith(JUnit4::class)
class ConstraintConnectionXmlDetectorTest : LintDetectorTest() {

    override fun getIssues(): MutableList<Issue> = mutableListOf(ReferenceViewIdXmlDetector.ISSUE)

    override fun getDetector(): Detector = ReferenceViewIdXmlDetector()

    @Test
    fun expectPass() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "res/layout/layout.xml",
                    """
                            <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                                xmlns:app="http://schemas.android.com/apk/res-auto"
                                android:layout_width="match_parent"
                                android:layout_height="match_parent">
                            
                                <TextView
                                    android:layout_width="wrap_content"
                                    android:layout_height="wrap_content"
                                    android:text="@string/app_name"
                                    app:layout_constraintTop_toBottomOf="@id/tv1"
                                    app:layout_constraintStart_toStartOf="@id/tv1"
                                    app:layout_constraintEnd_toEndOf="@id/tv1"
                                    app:layout_constraintBottom_toBottomOf="@id/tv1"
                                    />
                            </androidx.constraintlayout.widget.ConstraintLayout>
                        """.trimIndent()
                ).indented()
            ).run()
            .expectClean()
    }


    @Test
    fun expectFail() {
        lint()
            .allowMissingSdk()
            .files(
                xml("res/layout/layout.xml",
                    """
                    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent">
                    
                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:text="@string/app_name"
                            app:layout_constraintTop_toBottomOf="@+id/tv1"
                            app:layout_constraintStart_toStartOf="@+id/tv1"
                            app:layout_constraintEnd_toEndOf="@+id/tv1"
                            app:layout_constraintBottom_toBottomOf="@+id/tv1"
                            />
                    </androidx.constraintlayout.widget.ConstraintLayout>
                    """.trimIndent()
                ).indented()
            ).run()
            .expectErrorCount(4)
    }
}