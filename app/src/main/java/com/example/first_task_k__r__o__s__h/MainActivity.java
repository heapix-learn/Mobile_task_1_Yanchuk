package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {


    private ListView listTasks;
    public static String TODO_DOCUMENT = "ToDoDocuments";
    public static int TODO_NOTE_REQUEST=1;
    public static List<ToDoDocuments> listDocuments = new ArrayList<ToDoDocuments>();
    private TodoAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillList();
        Collections.sort(listDocuments);

        listTasks = (ListView) findViewById(R.id.listViewRow);
        listTasks.setOnItemClickListener(new ListViewClickListener());
        listTasks.setEmptyView(findViewById(R.id.emptyView));

        arrayAdapter = new TodoAdapter(this, listDocuments);
        listTasks.setAdapter(arrayAdapter);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        listDocuments.clear();
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
                showDocuments(toDoDocuments);
                return true;
            }
            case R.id.exit:{
                listDocuments.clear();
                Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            }
            case R.id.geolocation: {
                Intent myIntent = new Intent(this, MapsActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void fillList(){
        DBNotes database = new DBNotes(this);
        listDocuments=database.getNotesAllMy(LoginActivity.myUser.username);
        database.close();
    }

    private void showDocuments(ToDoDocuments toDoDocuments){
        Intent myIntent = new Intent(this, Note.class);

        myIntent.putExtra(TODO_DOCUMENT,toDoDocuments);
        startActivityForResult(myIntent, TODO_NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode ,Intent data){
        if (requestCode == TODO_NOTE_REQUEST){

            DBNotes database = new DBNotes(this);


            switch(resultCode){
                case RESULT_CANCELED: {
                    ToDoDocuments toDoDocuments =  data.getParcelableExtra("ToDoDocuments");
                    database.insertNote(toDoDocuments);
                    break;
                }
                case Note.RESULT_SAVE: {
                    ToDoDocuments toDoDocuments =  data.getParcelableExtra("ToDoDocuments");
                    addDocument(toDoDocuments);
                    database.insertNote(toDoDocuments);
                    break;
                }
                case Note.RESULT_DELETE: {
                    ToDoDocuments toDoDocuments = data.getParcelableExtra("ToDoDocuments");

                    deleteDocument(toDoDocuments);
                    break;
                }

                default:
                    break;

            }
            database.close();
        }
    }

    public void deleteDocument(ToDoDocuments toDoDocuments){
        listDocuments.remove(toDoDocuments);
        arrayAdapter.notifyDataSetChanged();

    }


    private void addDocument(ToDoDocuments toDoDocuments){
        if (toDoDocuments.getNumber()==-1) {
            toDoDocuments.setNumber(listDocuments.size());
            listDocuments.add(toDoDocuments);

        }   else {
            listDocuments.set(toDoDocuments.getNumber(), toDoDocuments);
        }
        Collections.sort(listDocuments);
        arrayAdapter.notifyDataSetChanged();
    }
    class ListViewClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            ToDoDocuments toDoDocuments=(ToDoDocuments) parent.getAdapter().getItem(position);
            toDoDocuments.setNumber(position);
            showDocuments(toDoDocuments);
        }
    }

}
