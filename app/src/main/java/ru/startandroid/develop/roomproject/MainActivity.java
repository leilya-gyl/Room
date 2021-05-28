package ru.startandroid.develop.roomproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.startandroid.develop.roomproject.roomdb.Driver;
import ru.startandroid.develop.roomproject.roomdb.DriverListAdapter;
import ru.startandroid.develop.roomproject.roomdb.MyViewModel;

public class MainActivity extends AppCompatActivity {

    private MyViewModel viewModel;
    RecyclerView recyclerView;

    public static final int NEW_DRIVER_ACIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        DriverListAdapter adapter = new DriverListAdapter(new DriverListAdapter.DriverDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get an existing or a new ViewModel from the ViewModelProvider
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(MyViewModel.class);

        viewModel.getDrivers().observe(this, list -> adapter.submitList(list));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewDriverActivity.class);
            startActivityForResult(intent, NEW_DRIVER_ACIVITY_REQUEST_CODE);
        });
        registerForContextMenu(recyclerView);
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

   /* public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        if (view.getId() == R.id.recyclerview) {
            menu.setHeaderTitle(viewModel.getDriver(((AdapterView.AdapterContextMenuInfo) menuInfo).position).toString());
            menu.add(0, 0, 0, "Отменить регистрацию");
            menu.add(0, 1, 0, "Отменить все регистрации");
        }
    }*/

    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        DriverListAdapter adapter = (DriverListAdapter) recyclerView.getAdapter();
        try{
            position = adapter.getPosition();
        } catch (Exception e) {
            Log.d("myLog", e.getLocalizedMessage(),e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case 0:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                long driver_id = adapter.getCurrentList()
                        .get(position)
                        ._id;
                Driver driver = viewModel.getDriver(driver_id);
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("Подтверждение");
                adb.setMessage("Действительно хотите отменить регистрацию?");
                adb.setPositiveButton("Да", (dialog, which) -> {
                    viewModel.delete(driver);
                    adapter.notifyDataSetChanged();
                });
                adb.setNegativeButton("Нет", null);
                adb.show();
                break;
            case 1:
                AlertDialog.Builder adb2 = new AlertDialog.Builder(this);
                adb2.setTitle("Подтверждение");
                adb2.setMessage("Действительно хотите отменить все регистрации?");
                adb2.setPositiveButton("Да", (dialog, which) -> {
                    viewModel.deleteAllDrivers();
                    adapter.notifyDataSetChanged();
                });
                adb2.setNegativeButton("Нет", null);
                adb2.show();
                break;
            default: break;
        }
        return true;
    }

    /*public void onClick(View view) {
        /view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Отменить регистрацию").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Driver driver = viewModel.getDriver(info.position);
                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setTitle("Подтверждение");
                        adb.setMessage("Действительно хотите отменить регистрацию?");
                        adb.setPositiveButton("Да", (dialog, which) -> {
                            viewModel.delete(driver);
                            adapter.notifyDataSetChanged();
                        });
                        adb.setNegativeButton("Нет", null);
                        adb.show();
                        return true;
                    }
                });
                menu.add("Отменить все регистрации").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder adb2 = new AlertDialog.Builder(MainActivity.this);
                        adb2.setTitle("Подтверждение");
                        adb2.setMessage("Действительно хотите отменить все регистрации?");
                        adb2.setPositiveButton("Да", (dialog, which) -> {
                            viewModel.deleteAllDrivers();
                            adapter.notifyDataSetChanged();
                        });
                        adb2.setNegativeButton("Нет", null);
                        adb2.show();
                        return true;
                    }
                });
            }
        });
    }*/
}