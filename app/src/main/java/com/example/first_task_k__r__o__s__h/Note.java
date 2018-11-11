package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.io.File;
import java.util.Date;
import java.util.Objects;

import static com.example.first_task_k__r__o__s__h.MainActivity.listDocuments;


public class Note extends AppCompatActivity {
    private EditText txtToDoDetails;
    private EditText textNote;
    private ToDoDocuments todoDocuments;
    public static final int RESULT_SAVE=100;
    public static final int RESULT_DELETE=101;
    private static final int NAME_LENGTH=20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        txtToDoDetails = (EditText) findViewById(R.id.textToDoDetails);
        todoDocuments = (ToDoDocuments)getIntent().getParcelableExtra("ToDoDocuments");
        textNote=(EditText) findViewById(R.id.note_text);

        txtToDoDetails.setText(todoDocuments.getTitle());
        textNote.setText(todoDocuments.getTextNote());
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
                if (txtToDoDetails.getText().toString().trim().length() == 0){
                    setResult(RESULT_CANCELED);
                }   else {
                    saveDocument();
                }
                finish();
                return true;
            }
            case R.id.save: {
                saveDocument();
                finish();
                return true;
            }
            case R.id.delete: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.confirm_delete);

                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_DELETE, getIntent());
                                finish();
                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                    });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveDocument(){
        StringBuilder sb = new StringBuilder(txtToDoDetails.getText());
        todoDocuments.setTitle(sb.toString());

        StringBuilder ss= new StringBuilder(textNote.getText());
        todoDocuments.setTextNote(ss.toString());

        if (sb.length()>NAME_LENGTH){
            sb.delete(NAME_LENGTH, sb.length()).append("...");
        }



        String tmpContext = sb.toString().trim().split("\n")[0];
        String context = (tmpContext.length()>0) ? tmpContext: todoDocuments.getContext();

        todoDocuments.setContext(context);

        if (todoDocuments.getContext()!=null && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
            setResult(RESULT_CANCELED, getIntent());
        } else {

            todoDocuments.setCreateDate(new Date());
            setResult(RESULT_SAVE, getIntent());

        }





        setResult(RESULT_SAVE, getIntent());
    }

}
