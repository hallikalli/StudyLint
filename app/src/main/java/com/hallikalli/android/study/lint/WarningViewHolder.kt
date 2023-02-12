package com.hallikalli.android.study.lint

import android.view.View
import androidx.recyclerview.widget.RecyclerView


class VhTest(itemView: View) : RecyclerView.ViewHolder(itemView) {}
class VHTest(itemView: View) : RecyclerView.ViewHolder(itemView) {}
class ViewHolderTest(itemView: View) : RecyclerView.ViewHolder(itemView) {}
class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
class TestVh(itemView: View) : RecyclerView.ViewHolder(itemView) {}
open class TestVH(itemView: View) : RecyclerView.ViewHolder(itemView) {}
class TestVH2(itemView: View):TestVH(itemView){}