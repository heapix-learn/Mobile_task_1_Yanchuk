package com.example.first_task_k__r__o__s__h;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;


public class Note extends AppCompatActivity {
    private EditText txtToDoDetails;
    private EditText textNote;
    private Switch switch_;
    private ToDoDocuments todoDocuments;
    private ImageView imageView;
    public static final int RESULT_SAVE=100;
    public static final int RESULT_DELETE=101;
    static final int GALLERY_REQUEST = 1;
    private static final int NAME_LENGTH=20;
    private Menu menu1;
    private boolean update=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);



        txtToDoDetails = (EditText) findViewById(R.id.textToDoDetails);

        todoDocuments = getIntent().getParcelableExtra("ToDoDocuments");
        textNote=(EditText) findViewById(R.id.note_text);



        if (todoDocuments.getLocationLatitude()<-999 && todoDocuments.getLocationLongitude()<-999) {
            MyLocationListener.SetUpLocationListener(this);
            todoDocuments.setLocation("" + MyLocationListener.imHere.getLatitude()+"/"+MyLocationListener.imHere.getLongitude());
        }
        else {
            DBNotes database = new DBNotes(this);
            database.deleteNote(todoDocuments);
            database.close();
        }

        imageView = (ImageView) findViewById(R.id.imageView);

        if (todoDocuments.getImagePath()!=null) {

            Bitmap bitmap = null;
            Uri selectedImage = todoDocuments.getImagePath();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }



        txtToDoDetails.setText(todoDocuments.getTitle());
        textNote.setText(todoDocuments.getTextNote());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.todo_details, menu);

        if (todoDocuments.getAccess()==0) {
            MenuItem item = menu.findItem(R.id.publicc);
            item.setChecked(true);
        }
        else {
            MenuItem item = menu.findItem(R.id.privatee);
            item.setChecked(true);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.privatee: {
                item.setChecked(true);
                todoDocuments.setAccess(1);
                update=true;
                return true;
            }
            case R.id.publicc: {
                item.setChecked(true);
                todoDocuments.setAccess(0);
                update=true;
                return true;
            }
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

            case R.id.gallery:{
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                update=true;
                return true;
            }

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void saveDocument(){
        StringBuilder sb = new StringBuilder(txtToDoDetails.getText());


        StringBuilder ss= new StringBuilder(textNote.getText());


        if (sb.length()>NAME_LENGTH){
            sb.delete(NAME_LENGTH, sb.length()).append("...");
        }



        String tmpContext = sb.toString().trim().split("\n")[0];
        String context = (tmpContext.length()>0) ? tmpContext: todoDocuments.getContext();



        if (update == false && todoDocuments.getContext()!=null && txtToDoDetails.getText().toString().equals(todoDocuments.getTitle()) && textNote.getText().toString().equals(todoDocuments.getTextNote())){
            todoDocuments.setTitle(sb.toString());
            todoDocuments.setTextNote(ss.toString());
            todoDocuments.setContext(context);
            setResult(RESULT_CANCELED, getIntent());
        } else {
            todoDocuments.setTitle(sb.toString());
            todoDocuments.setTextNote(ss.toString());
            todoDocuments.setContext(context);
            //todoDocuments.setCreateDate(new Date());
            setResult(RESULT_SAVE, getIntent());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        imageView = (ImageView) findViewById(R.id.imageView);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    assert selectedImage != null;
                    todoDocuments.setImagePath(selectedImage);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }

}
