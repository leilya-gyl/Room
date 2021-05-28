package ru.startandroid.develop.roomproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.startandroid.develop.roomproject.roomdb.Driver;
import ru.startandroid.develop.roomproject.roomdb.DriverListAdapter;
import ru.startandroid.develop.roomproject.roomdb.MyViewModel;

public class MainActivity extends AppCompatActivity {

    private MyViewModel viewModel;

    public static final int NEW_DRIVER_ACIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final DriverListAdapter adapter = new DriverListAdapter(new DriverListAdapter.DriverDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get an existing or a new ViewModel from the ViewModelProvider
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(MyViewModel.class);

        viewModel.getDrivers().observe(this, adapter::submitList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewDriverActivity.class);
            startActivityForResult(intent, NEW_DRIVER_ACIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_DRIVER_ACIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Driver driver = new Driver(data.getLongExtra(NewDriverActivity.EXTRA_REPLY_ID, 0)
                    , data.getStringExtra(NewDriverActivity.EXTRA_REPLY_NAME)
                    , 0);
            viewModel.insert(driver);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }
    }
}