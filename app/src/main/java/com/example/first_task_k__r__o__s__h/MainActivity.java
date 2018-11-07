package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.objects.ToDoDocuments;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listTasks;
    public static String TODO_DOCUMENT = "com.example.objects.ToDoDocuments";
    public static int TODO_NOTE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listTasks = (ListView) findViewById(R.id.listViewRow);
        fillList();
    }


    public void Exit(View view){
         Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
         MainActivity.this.startActivity(myIntent);
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void fillList(){
        ToDoDocuments d1= new ToDoDocuments("Title1");
        ToDoDocuments d2= new ToDoDocuments("Title2");
        List<ToDoDocuments> listDocuments = new ArrayList<ToDoDocuments>();
        listDocuments.add(d1);
        listDocuments.add(d2);
        ArrayAdapter<ToDoDocuments> arrayAdapter = new ArrayAdapter<ToDoDocuments>(this, R.layout.listview_row, listDocuments);
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
                case RESULT_CANCELED:
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
                case Note.RESULT_SAVE:
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;

            }
        }
    }
}
