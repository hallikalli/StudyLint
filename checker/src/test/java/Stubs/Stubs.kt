package Stubs

import com.android.tools.lint.checks.infrastructure.LintDetectorTest

object Stubs {
    val STUB_RECYCLE = LintDetectorTest.kotlin(
        """
            package androidx.recyclerview.widget

            open class RecyclerView {
               open class ViewHolder 
            }
     """
    )

    val STUB_VIEW = LintDetectorTest.kotlin(
        """
        package android.view
            class View
     """
    )
}