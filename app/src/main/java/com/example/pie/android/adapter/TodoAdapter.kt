package com.example.pie.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

import com.example.pie.android.R
import com.example.pie.android.model.TodoItem
import com.example.pie.android.rest.resource.TodoResource

class TodoAdapter(context: Context, resource: Int, objects: List<TodoItem>) : ArrayAdapter<TodoItem>(context, resource, objects) {

    override fun getView(index: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.todo_item, parent, false)

        val task = getItem(index)

        if (task != null) {
            val isDone = view.findViewById<View>(R.id.done) as CheckBox
            val label = view.findViewById<View>(R.id.task) as TextView

            isDone.isChecked = task.isDone
            isDone.isEnabled = !task.isDone
            label.text = task.task

            isDone.setOnClickListener {
                if (isDone.isChecked) {
                    TodoResource().completeItem(task.id).subscribe({
                        task.complete()
                        isDone.isEnabled = false
                    }, { throwable -> throwable.printStackTrace() })
                }
            }
        }

        return view
    }
}
