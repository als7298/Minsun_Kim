package edu.qc.seclass.glm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonCompute;
    ArrayList<String> nameOfList = null;
    ArrayAdapter adapter = null;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonCompute = findViewById(R.id.btnCheckList);

        nameOfList = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buttonCompute.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("TAG", "Check List Button Clicked");
                try{
                    String output;
                    FileInputStream file = openFileInput("ListFile");
                    InputStreamReader in = new InputStreamReader(file);
                    BufferedReader br = new BufferedReader(in);
                    //StringBuffer sb = new StringBuffer();s
                    while((output=br.readLine()) != null){
                        nameOfList.add(output);
                    }
                    adapter = new ArrayAdapter(CheckList.this, android.R.layout.simple_list_item_1, nameOfList);
                    lv = findViewById(R.id.listOfList);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent cl = new Intent(CheckList.this, ChoosenList.class);
                            cl.putExtra("filename",  nameOfList.get(i));
                            startActivity(cl);
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.home){
            Intent h = new Intent(this, MainActivity.class);
            startActivity(h);
            return false;
        }else if (id == R.id.checkList) {
            Intent ch = new Intent(this, CheckList.class);
            startActivity(ch);
            return false;
        } else if (id == R.id.deleteList) {
            Intent d = new Intent(this, DeleteList.class);
            startActivity(d);
            return false;
        } else if (id == R.id.createList) {
            Intent cr = new Intent(this, CreateList.class);
            startActivity(cr);
            return false;
        } else if(id == R.id.renameList){
            Intent rn = new Intent(this, RenameList.class);
            startActivity(rn);
            return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
