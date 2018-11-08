package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ListView listTasks;
    public static String TODO_DOCUMENT = "ToDoDocuments";
    public static int TODO_NOTE_REQUEST=1;
    private static List<ToDoDocuments> listDocuments = new ArrayList<ToDoDocuments>();
    private ArrayAdapter<ToDoDocuments> arrayAdapter;
    private int kol=-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listTasks = (ListView) findViewById(R.id.listViewRow);
        listTasks.setOnItemClickListener(new ListViewClickListener());

        fillList();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.add_task: {
                ToDoDocuments toDoDocuments = new ToDoDocuments();
                toDoDocuments.setTitle(getResources().getString(R.string.new_document));
                showDocuments(toDoDocuments);
                return true;
            }
            case R.id.exit:{
                Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void fillList(){

        arrayAdapter = new ArrayAdapter<ToDoDocuments>(this, R.layout.listview_row, listDocuments);
        listTasks.setAdapter(arrayAdapter);
    }
    private void showDocuments(ToDoDocuments toDoDocuments){
        Intent myIntent = new Intent(this, Note.class);
        myIntent.putExtra(TODO_DOCUMENT,toDoDocuments);
        startActivityForResult(myIntent, TODO_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode ,Intent data){
        if (requestCode == TODO_NOTE_REQUEST){


            switch(resultCode){
                case RESULT_CANCELED: {
                    break;
                }
                case Note.RESULT_SAVE: {
                    ToDoDocuments toDoDocuments = (ToDoDocuments) data.getParcelableExtra("ToDoDocuments");
                    addDocument(toDoDocuments);
                    break;
                }
                case Note.RESULT_DELETE: {
                    ToDoDocuments toDoDocuments = (ToDoDocuments) data.getParcelableExtra("ToDoDocuments");
                    deleteDocument(toDoDocuments);

                    break;
                }
                default:
                    break;

            }
        }
    }

    private void deleteDocument(ToDoDocuments toDoDocuments){
        listDocuments.remove(toDoDocuments);
        arrayAdapter.notifyDataSetChanged();
    }
    private void addDocument(ToDoDocuments toDoDocuments){
    //    kol++;
   //     toDoDocuments.setNumber(kol);
        listDocuments.add(toDoDocuments);
        toDoDocuments.setNumber(listDocuments.indexOf(toDoDocuments));
        arrayAdapter.notifyDataSetChanged();
    }
    class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            ToDoDocuments toDoDocuments=(ToDoDocuments) parent.getAdapter().getItem(position);
            showDocuments(toDoDocuments);
        }
    }

}
