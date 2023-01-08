package com.hallikalli.android.study.lint.checker

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.*

class IssueRegistry : IssueRegistry() {
    override val api: Int
        get() = CURRENT_API

    override val issues: List<Issue>
        get() = listOf(
            CollectionsUseOrNullDetector.ISSUE,
            ReferenceViewIdXmlDetector.ISSUE
        )
}