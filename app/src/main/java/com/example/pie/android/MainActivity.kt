package com.example.pie.android

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

import com.example.pie.android.adapter.TodoAdapter
import com.example.pie.android.model.TodoItem
import com.example.pie.android.rest.resource.TodoResource

import java.util.ArrayList

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: ArrayAdapter<TodoItem>
    private lateinit var refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refresh = findViewById<View>(R.id.refresh) as SwipeRefreshLayout
        refresh.setOnRefreshListener(this)
        adapter = TodoAdapter(this, R.layout.todo_item, ArrayList())

        val list = findViewById<View>(R.id.list) as ListView
        list.adapter = adapter
        retrieveItems()
    }

    fun retrieveItems() {
        TodoResource().items.subscribe({ todoItems ->
            // This should probably be made more efficient.
            adapter.clear()
            adapter.addAll(todoItems)
            refresh.isRefreshing = false
        }) { throwable ->
            refresh.isRefreshing = false
            Toast.makeText(this@MainActivity, "Failed to contact Server.", Toast.LENGTH_SHORT).show()
            throwable.printStackTrace()
        }
    }

    fun submitItem(v: View) {
        val input = findViewById<View>(R.id.newTask) as EditText
        val userInput = input.text.toString().trim { it <= ' ' }
        if (userInput.isNotEmpty()) {
            val newItem = TodoItem()
            newItem.task = userInput
            val newItems = ArrayList<TodoItem>()
            newItems.add(newItem)

            TodoResource().addItems(newItems).subscribe({ todoItems ->
                adapter.addAll(todoItems)
                input.setText("")
            }) { throwable ->
                Toast.makeText(this@MainActivity, "Failed to contact Server.", Toast.LENGTH_SHORT).show()
                throwable.printStackTrace()
            }
        }
    }

    fun clearCompletedTasks(v: View) {
        val input = findViewById<View>(R.id.newTask) as EditText
        TodoResource().clearDone().subscribe({ todoItems ->
            adapter.clear()
            adapter.addAll(todoItems)
            input.setText("")
        }) { throwable -> throwable.printStackTrace() }
    }

    override fun onRefresh() {
        retrieveItems()
    }

    public override fun onRestart() {
        super.onRestart()
        retrieveItems()
    }
}
