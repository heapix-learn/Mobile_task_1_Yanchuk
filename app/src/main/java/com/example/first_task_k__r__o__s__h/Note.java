package com.example.first_task_k__r__o__s__h;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.objects.ToDoDocuments;

public class Note extends AppCompatActivity {
    private EditText mTitle;
    public static final int RESULT_SAVE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ToDoDocuments toDoDocuments = (ToDoDocuments)getIntent().getSerializableExtra(MainActivity.TODO_DOCUMENT);
        setTitle(toDoDocuments.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.todo_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.back: {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            }
            case R.id.save: {
                setResult(RESULT_SAVE);
                finish();
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

   public void buttonAdd(View v) {
        mTitle = (EditText) findViewById(R.id.title);
        String Titlee = mTitle.getText().toString();
        Intent intent = new Intent(Note.this, MainActivity.class);
        intent.putExtra("ToDoDocuments", new ToDoDocuments(Titlee));
        Note.this.startActivity(intent);
    }

}
