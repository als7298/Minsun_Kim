package edu.qc.seclass.glm;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class RenameList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> nameOfList = null;
    ArrayAdapter adapter = null;
    ListView lv = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        try {
            //read from file each list element
            String output;
            FileInputStream file = openFileInput("ListFile");
            InputStreamReader in = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(in);    //read each line in the text file

            while ((output = br.readLine()) != null) {
                nameOfList.add(output);
            } //end while
            file.close(); //close the file  //close after use

            //have an adapter
            adapter = new ArrayAdapter(RenameList.this, android.R.layout.simple_list_item_1, nameOfList); //check this
            lv = findViewById(R.id.istView_delete_List);
            lv.setAdapter(adapter);

            //click an item

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    //get the name of the list to rename
                    final String tempNameOfListnameOfList=nameOfList.get(i);  //get on toast to test

                    //input name to rename
                    final String[] inputUser = {new String()};  //input holds the newname of file

                    AlertDialog.Builder ab= new AlertDialog.Builder(RenameList.this);
                    ab.setTitle("Rename list");
                    //create an edittext for users to enter data
                    final EditText et= new EditText(RenameList.this);
                    //add edittext to dialogue
                    ab.setView(et);
                    //dialog has two buttons
                    ab.setPositiveButton("RENAME", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            inputUser[0] =et.getText().toString();
                            String newName = inputUser[0];
                            nameOfList.set(i, inputUser[0]);        //set the index to the string

                            //write back to file

                            try {
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput("ListFile", Context.MODE_PRIVATE));
                                int start=0;

                                while(start<nameOfList.size()){
                                    outputStreamWriter.write(nameOfList.get(start)+'\n');
                                    start++;
                                } //end while
                                outputStreamWriter.close();

                                String dir = getFilesDir().getAbsolutePath();
                                String originalN = tempNameOfListnameOfList + ".txt";
                                String newN = newName + ".txt";
                                File Ofile = new File(dir, originalN);
                                File nfile = new File(dir, newN);
                                if(Ofile.exists()){
                                    Ofile.renameTo(nfile);
                                }


                                Intent inte = new Intent(RenameList.this, RenameList.class);
                                startActivity(inte);
                            }
                            catch (IOException e) {
                                Log.e("Exception", "File write failed: " + e.toString());
                            }


                            //completed
                            Toast.makeText(RenameList.this, "Renamed to  "+ inputUser[0], Toast.LENGTH_LONG).show();

                        }
                    });
                    ab.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing

                        }
                    });
                    //onClick show

                    AlertDialog a=ab.create();
                    a.show();


                }  //end onItemClick

            });

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
