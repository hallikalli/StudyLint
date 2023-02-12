package Stubs

import com.android.tools.lint.checks.infrastructure.LintDetectorTest

object CollectionStub {
    val STUB_COLLECTIONS = LintDetectorTest.kotlin(
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
}