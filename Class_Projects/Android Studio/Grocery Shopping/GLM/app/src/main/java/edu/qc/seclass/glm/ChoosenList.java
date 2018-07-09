package edu.qc.seclass.glm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ChoosenList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> listData ;
    ArrayAdapter adapter;
    ListView lv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosen_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button buttonAdd = findViewById(R.id.button_add);
        listData = new ArrayList<>();
        Bundle getData = getIntent().getExtras();
        String filename = getData.getString("filename");
        final String originalName = filename;
        filename = filename + ".txt";

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

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            String output;
            FileInputStream file = null;
            Log.d("TAG", "Try to view inside of txt file.");
            file = openFileInput(filename);
            InputStreamReader in = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(in);
            Log.d("TAG", "View inside now.");

            String data = br.readLine();
            int numOfProduct = Integer.parseInt(data);
            if (numOfProduct == 0) {
                Toast.makeText(getApplicationContext(), "You list is empty.",
                        Toast.LENGTH_LONG).show();
            } else if (numOfProduct > 0) {
                while ((output = br.readLine()) != null) {
                    listData.add(output);
                }
                //listData.add("test");
                adapter = new ArrayAdapter(ChoosenList.this, android.R.layout.simple_list_item_1, listData);
                lv = findViewById(R.id.listOfList1);
                lv.setAdapter(adapter);

                final String finalFilename = filename;
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        final String tempNameOfListnameOfList=listData.get(i);
                        new AlertDialog.Builder(ChoosenList.this).setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Are you sure")
                                .setMessage("Are you sure to delete?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("TAG", "********************clicked**");
                                        String tempOutput;
                                        try {
                                            FileInputStream filef = openFileInput(finalFilename);
                                            InputStreamReader inn = new InputStreamReader(filef);
                                            BufferedReader brr = new BufferedReader(inn);
                                            while ((tempOutput = brr.readLine()) != null) {
                                                if (tempOutput.equals(tempNameOfListnameOfList)) {
                                                    String n = listData.get(0);
                                                    int num = Integer.parseInt(n);
                                                    num = num-1;
                                                    listData.add(0, String.valueOf(num));
                                                    int j = i;
                                                    j = j + 1;
                                                    listData.remove(j);
                                                    j = j+1;
                                                    listData.remove(j);
                                                    Log.d("TAG", "delete item.");
                                                }
                                            }
                                            try {
                                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(finalFilename, Context.MODE_PRIVATE));
                                                int start=0;
                                                while(start<listData.size()){
                                                    outputStreamWriter.write(listData.get(start)+'\n');
                                                    start++;
                                                }
                                                outputStreamWriter.close();
                                                Log.d("TAG", "updated");

                                                Intent inte = new Intent(ChoosenList.this, ChoosenList.class);
                                                inte.putExtra("filename",  originalName);
                                                startActivity(inte);

                                            }catch (IOException e) {
                                                Log.e("Exception", "File write failed: " + e.toString());
                                            }

                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "There is an error.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
