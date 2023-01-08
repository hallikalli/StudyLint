package com.hallikalli.android.study.lint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import kotlin.collections.max
import kotlin.collections.*

@SuppressWarnings("unused")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun collectionsUseOrNullDetectorPreview() {
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