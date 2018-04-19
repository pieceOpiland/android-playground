package com.example.pie.android

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast

import com.example.pie.android.adapter.TodoAdapter
import com.example.pie.android.model.TodoItem
import com.example.pie.android.rest.RetrofitProvider
import com.example.pie.android.rest.resource.TodoResource

import java.util.ArrayList

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: ArrayAdapter<TodoItem>
    private val items = ArrayList<TodoItem>()
    private val resource: TodoResource = TodoResource(RetrofitProvider.instance.create(TodoResource.TodoApi::class.java))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refresh.setOnRefreshListener(this)
        adapter = TodoAdapter(resource, this, R.layout.todo_item, items)

        list.adapter = adapter
        if (savedInstanceState != null) {
            adapter.addAll(savedInstanceState.getParcelableArrayList(ITEMS_PARAM))
        } else {
            retrieveItems()
        }
    }

    private fun retrieveItems() {
        resource.items.subscribe({ todoItems ->
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

            resource.addItems(newItems).subscribe({ todoItems ->
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
        resource.clearDone().subscribe({ todoItems ->
            adapter.clear()
            adapter.addAll(todoItems)
            input.setText("")
        }) { throwable -> throwable.printStackTrace() }
    }

    override fun onRefresh() {
        retrieveItems()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(ITEMS_PARAM, items)
    }

    companion object {
        private const val ITEMS_PARAM = "items"
    }
}
