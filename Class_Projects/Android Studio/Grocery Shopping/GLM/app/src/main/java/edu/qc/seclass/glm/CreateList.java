package edu.qc.seclass.glm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CreateList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonCompute = findViewById(R.id.btnCreateList);
        final EditText inputName = findViewById(R.id.newListName);

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
                Log.d("TAG", "Create List  Button Clicked");
                if(TextUtils.isEmpty(inputName.getText().toString())){
                    Toast.makeText(CreateList.this, "Enter the name of a new list.", Toast.LENGTH_LONG).show();
                } else {
                    String fileName = inputName.getText().toString() + ".txt";
                    try{
                        FileOutputStream file = openFileOutput("ListFile", MODE_APPEND);
                        file.write(inputName.getText().toString().getBytes());
                        file.write("\n".getBytes());
                        file.close();
                        Log.d("TAG", "Saved file name into data file.");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        OutputStreamWriter of = new OutputStreamWriter(getApplicationContext().openFileOutput(fileName, Context.MODE_APPEND));
                        of.write("0\n");
                        of.close();
                        Toast.makeText(CreateList.this, "File created.", Toast.LENGTH_LONG).show();
                        Log.d("TAG", "Saved file name into data file.");
                        inputName.setText("");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
