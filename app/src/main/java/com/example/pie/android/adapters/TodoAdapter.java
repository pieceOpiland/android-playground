package com.example.pie.android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pie.android.R;
import com.example.pie.android.models.TodoItem;
import com.example.pie.android.resources.TodoResource;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

    public TodoAdapter(Context context, int resource, List<TodoItem> objects) {
        super(context, resource, objects);
    }

    @Override
    @NonNull
    public View getView(int index, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        final TodoItem task = getItem(index);

        if(task != null) {
            final CheckBox isDone = (CheckBox) convertView.findViewById(R.id.done);
            TextView label = (TextView) convertView.findViewById(R.id.task);

            isDone.setChecked(task.isDone());
            isDone.setEnabled(!task.isDone());
            label.setText(task.getTask());

            isDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isDone.isChecked()) {
                        TodoResource.getInstance().finishItem(task.getId(), new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                task.complete();
                                isDone.setEnabled(false);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
            });
        }


        return convertView;
    }
}
