package com.example.pie.android

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.pie.android.adapter.TodoAdapter
import com.example.pie.android.databinding.ActivityMainBinding
import com.example.pie.android.model.TodoItem
import com.example.pie.android.rest.TodoResource
import dagger.android.support.DaggerAppCompatActivity

import java.util.ArrayList

import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: ArrayAdapter<TodoItem>
    private lateinit var binding: ActivityMainBinding
    private val items = ArrayList<TodoItem>()
    @Inject lateinit var resource: TodoResource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refresh.setOnRefreshListener(this)
        adapter = TodoAdapter(resource, this, R.layout.todo_item, items)

        binding.list.adapter = adapter
        if (savedInstanceState != null) {
            adapter.addAll(savedInstanceState.getParcelableArrayList(ITEMS_PARAM) ?: emptyList())
        } else {
            retrieveItems()
        }
    }

    private fun retrieveItems() {
        resource.items.subscribe({ todoItems ->
            // This should probably be made more efficient.
            adapter.clear()
            adapter.addAll(todoItems)
            binding.refresh.isRefreshing = false
        }) { throwable ->
            binding.refresh.isRefreshing = false
            Toast.makeText(this, "Failed to contact Server.", Toast.LENGTH_SHORT).show()
            throwable.printStackTrace()
        }
    }

    fun submitItem(v: View) {
        val userInput = binding.newTask.text.toString().trim { it <= ' ' }
        if (userInput.isNotEmpty()) {
            val newItem = TodoItem()
            newItem.task = userInput
            val newItems = ArrayList<TodoItem>()
            newItems.add(newItem)

            resource.addItems(newItems).subscribe({ todoItems ->
                adapter.addAll(todoItems)
                binding.newTask.setText("")
            }) { throwable ->
                Toast.makeText(this, "Failed to contact Server.", Toast.LENGTH_SHORT).show()
                throwable.printStackTrace()
            }
        }
    }

    fun clearCompletedTasks(v: View) {
        resource.clearDone().subscribe({ todoItems ->
            adapter.clear()
            adapter.addAll(todoItems)
            binding.newTask.setText("")
        }) { throwable -> throwable.printStackTrace() }
    }

    override fun onRefresh() {
        retrieveItems()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ITEMS_PARAM, items)
    }

    companion object {
        private const val ITEMS_PARAM = "items"
    }
}
