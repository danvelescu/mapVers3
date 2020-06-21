package com.example.mapvers3.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mapvers3.ContentPage;
import com.example.mapvers3.DataBaseClient;
import com.example.mapvers3.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends Activity {
    private Button buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask= findViewById(R.id.updatedatabase);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.ASSERT,"alert","Jobe DOne");
                getTasks();
            }
        });

    }
    private void getTasks()
    {
        class GetTasks extends AsyncTask<Void,Void, List<ContentPage>>{

            @Override
            protected List<ContentPage> doInBackground(Void... voids) {
               List<ContentPage> listofcontent = DataBaseClient
                       .getInstance(getApplicationContext())
                       .getAppDatabase()
                       .contentDao()
                       .getAll();
                Log.println(Log.ASSERT,"alert","Jobe DOne");
               return listofcontent;
            }

            @Override
            protected void onPostExecute(List<ContentPage> contentPages) {
                super.onPostExecute(contentPages);
                ContentAdapter adapter = new ContentAdapter(HomeActivity.this,contentPages);
                recyclerView.setAdapter(adapter);
                Log.println(Log.ASSERT,"alert","Jobe DOne");
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
