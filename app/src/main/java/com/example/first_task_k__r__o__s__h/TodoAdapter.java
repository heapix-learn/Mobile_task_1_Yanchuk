package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;

public class TodoAdapter extends ArrayAdapter<ToDoDocuments> {
    private List<ToDoDocuments> list;

    public TodoAdapter(Context context, List<ToDoDocuments> list){
        super(context, R.layout.listview_row, R.id.todo_name, list);
        this.list=list;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        if (convertView==null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.todoDate = (TextView) convertView.findViewById(R.id.todo_date);
            viewHolder.todoName = (TextView) convertView.findViewById(R.id.todo_name);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder= (ViewHolder) convertView.getTag();
        ToDoDocuments toDoDocuments = list.get(position);

        holder.todoName.setText((toDoDocuments.getTitle()));
        holder.todoDate.setText(android.text.format.DateFormat.format("dd/MM/yyyy, hh:mm",toDoDocuments.getCreateDate()));
        return convertView;
    }
    static class ViewHolder{
        public TextView todoName;
        public TextView todoDate;
    }
}
