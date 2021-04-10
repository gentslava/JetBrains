package com.example.materialcomponentsexample.presentation.recycler.list

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.materialcomponentsexample.R
import com.example.materialcomponentsexample.data.ExampleBigItem

class ExampleBigViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var textTitle: TextView
    private lateinit var textSubtitle: TextView

    fun bind(item: ExampleBigItem) {
        textTitle = itemView.findViewById(R.id.text_view_title)
        textSubtitle = itemView.findViewById(R.id.text_view_subtitle)
        textTitle.text = item.title
        textTitle.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(itemView.context, item.icon),
            null,
            null,
            null)
        textSubtitle.text = item.subtitle
    }
}