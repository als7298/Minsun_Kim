package edu.qc.seclass.glm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProductListAdapter.ProducListClickListener, GroceryListAdapter.GroceryListClickListener {

    private ProductListAdapter productAdapter;
    private RecyclerView recView;
    private List<String> productList = Arrays.asList(
            "Apple", "Avocado", "Lime", "Lemon", "Onion", "Banana", "Egg", "Milk", "Orange",
            "Beef", "Pork Belly", "Chicken Wing", "Garlic", "Cheese", "Candy", "Dog food", "Cat food",
            "Potato", "Sweet Potato", "Chips");

    private List<GroceryList> groceryLists = new ArrayList<>();


    //Represents the position of the currently selected list. Can only remember one list
    private Collection<Integer> currentlyCheckedGroceryList = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accessStoredLists();

        productAdapter = new ProductListAdapter(productList, this);
        recView = findViewById(R.id.recview_products);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(productAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void accessStoredLists() {

        try {
            String output;
            FileInputStream file = openFileInput("ListFile");
            InputStreamReader in = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(in);
            while ((output = br.readLine()) != null) {
                GroceryList groceryList1 = new GroceryList(output);
                groceryLists.add(groceryList1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*private void populateListFromFile(GroceryList userList, File currentFile) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(currentFile));
        try {
            //First number discar
            reader.readLine();
            String lineFromFile = reader.readLine();
            while(lineFromFile != null) {
                String name = lineFromFile;
                int lemon = Integer.parseInt(reader.readLine());
                userList.addItemToList(name, lemon);
                lineFromFile = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        groceryLists.add(userList);
        Log.d("List", userList.toString());
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            Collections.sort(productList);
            recView.setAdapter(productAdapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void productClicked(final String item) {
        //MAKE A NEW ACTIVITY
        //File groceryListFile = new File(getFilesDir(), "groceryList");
        //PrintWriter file = new PrintWriter(new File(groceryLists, fileName));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText(item);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(30f);
        builder.setCustomTitle(title);

        View view = getLayoutInflater().inflate(R.layout.activity_add_item, null);
        final NumberPicker numberPicker = view.findViewById(R.id.numberPicker_quantity);
        RecyclerView groceryListRecyclerView = view.findViewById(R.id.recycler_grocery_lists);
        groceryListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryListRecyclerView.setAdapter(new GroceryListAdapter(groceryLists, this));


        numberPicker.setMaxValue(999);
        numberPicker.setMinValue(0);
        currentlyCheckedGroceryList.clear();
        builder.setView(view);
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int quantityChosen = numberPicker.getValue();
                for(int checkedGroceryListPosition : currentlyCheckedGroceryList) {
                    GroceryList groceryList = groceryLists.get(checkedGroceryListPosition);
                    groceryList.addItemToList("",item, quantityChosen);
                    saveToFile(groceryList);
                    String message = String.format("%d %s(s) added to %s", quantityChosen, item, groceryList.getName());
                    Log.d("Checkbox Test", message);
                    Log.d("Checkbox Test", groceryList.toString());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),  "CANCELED", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void saveToFile(GroceryList groceryList) {
        File groceryLists = new File(getFilesDir(), groceryList.getName() + ".txt");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(groceryLists));
            LinkedHashMap<String, Integer> listProducts = groceryList.getProducts();
            pw.println(listProducts.size());
            for(Map.Entry<String, Integer> productEntry : listProducts.entrySet()) {
                pw.println(productEntry.getKey());
                pw.println(productEntry.getValue());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void groceryListClicked(int position) {
        //if its there remove it
        //else add it
        if(currentlyCheckedGroceryList.contains(position)) {
            currentlyCheckedGroceryList.remove(position);
        } else {
            currentlyCheckedGroceryList.add(position);
        }
        String message = String.format("%s was clicked", groceryLists.get(position).getName());
        Log.d("Checkbox Test", currentlyCheckedGroceryList.toString());
        Toast.makeText(this,  message, Toast.LENGTH_LONG).show();
    }
}
