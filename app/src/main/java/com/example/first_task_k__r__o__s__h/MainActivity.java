package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        listTasks = (ListView) findViewById(R.id.listViewRow);
        listTasks.setOnItemClickListener(new ListViewClickListener());
        listTasks.setEmptyView(findViewById(R.id.emptyView));

        arrayAdapter = new TodoAdapter(this, listDocuments);
        listTasks.setAdapter(arrayAdapter);

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


        File prefer = new File(getApplicationInfo().dataDir, "shared_prefs");
        if (prefer.exists() && prefer.isDirectory()){
            String[] list = prefer.list();
            for (String aList : list) {
                SharedPreferences sharedPref = getSharedPreferences(aList.replace(".xml", ""), Context.MODE_PRIVATE);
                ToDoDocuments toDoDocuments = new ToDoDocuments();
                toDoDocuments.setTitle(sharedPref.getString(AppContext.FIELD_TITLE, null));
                toDoDocuments.setNumber(sharedPref.getInt(AppContext.FIELD_NUMBER, 0));
                toDoDocuments.setCreateDate(new Date(sharedPref.getLong(AppContext.FIELD_CREATE_DATE, 0)));
                toDoDocuments.setLogin(sharedPref.getString(AppContext.FIELD_LOGIN, null));
                toDoDocuments.setContext(sharedPref.getString(AppContext.FIELD_CONTEXT, null));
                toDoDocuments.setTextNote(sharedPref.getString(AppContext.FIELD_TEXT_NOTE, null));
                listDocuments.add(toDoDocuments);
            }
        }


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

    public void deleteDocument(ToDoDocuments toDoDocuments){
        File file = getCirrentTodoFile(toDoDocuments);
        if (file.exists()) {
            if (file.delete()) {
                listDocuments.remove(toDoDocuments);
            }

        }
        arrayAdapter.notifyDataSetChanged();
    }

    private File getCirrentTodoFile(ToDoDocuments toDoDocuments){
        String filePath = ((AppContext) getApplicationContext()).getPrefsDir() + "/" + toDoDocuments.getCreateDate().getTime() + ".xml";
        return new File(filePath);
    }

    private void addDocument(ToDoDocuments toDoDocuments){
        if (toDoDocuments.getNumber()==-1){
            listDocuments.add(toDoDocuments);
            try{

                SharedPreferences sharedPref= getSharedPreferences(String.valueOf(toDoDocuments.getCreateDate().getTime()), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(AppContext.FIELD_TITLE, toDoDocuments.getTitle());
                editor.putInt(AppContext.FIELD_NUMBER, toDoDocuments.getNumber());
                editor.putLong(AppContext.FIELD_CREATE_DATE, toDoDocuments.getCreateDate().getTime());
                editor.putString(AppContext.FIELD_LOGIN, toDoDocuments.getLogin());
                editor.putString(AppContext.FIELD_CONTEXT, toDoDocuments.getContext());
                editor.putString(AppContext.FIELD_TEXT_NOTE, toDoDocuments.getTextNote());
                editor.apply();
            }   catch (Exception e){
                //     log.e("error", e.getMessage());
            }
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
