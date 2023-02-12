package com.hallikalli.android.study.lint

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.*

@SuppressWarnings("unused")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    data class ASDF(val id: String) {
        fun get(id:String) {}
    }

    fun collectionsUseOrNullDetectorPreview() {
        arrayListOf<Int>().get(0)
        arrayListOf<Int>()[0]
        ASDF("asdf").get("string")
        val list2 = doubleArrayOf()
        list2[0]
        doubleArrayOf().get(0)
        floatArrayOf().get(0)
        longArrayOf().get(0)
        intArrayOf().get(0)
        charArrayOf().get(0)
        shortArrayOf().get(0)
        byteArrayOf().get(0)
        booleanArrayOf().get(0)
        val list = listOf(1, 2)
        list.get(0)
        list[0]
        listOf<Int>()[0]
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
