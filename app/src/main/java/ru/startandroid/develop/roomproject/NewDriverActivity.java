package ru.startandroid.develop.roomproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewDriverActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_ID = "ru.startandroid.develop.roomproject2.REPLY.ID";
    public static final String EXTRA_REPLY_NAME = "ru.startandroid.develop.roomproject2.REPLY.NAME";

    private EditText driverId;
    private EditText driverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_driver);

        driverId = findViewById(R.id.edit_id);
        driverName = findViewById(R.id.edit_name);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if(TextUtils.isEmpty(driverId.getText().toString()) || TextUtils.isEmpty(driverName.getText().toString()) ) {
                setResult(RESULT_CANCELED, replyIntent);
            }else {
                String name = driverName.getText().toString();
                long id = Long.parseLong(driverId.getText().toString());
                replyIntent.putExtra(EXTRA_REPLY_ID, id);
                replyIntent.putExtra(EXTRA_REPLY_NAME, name);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}