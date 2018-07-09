package edu.qc.seclass.glm;

import android.content.Context;
import android.widget.Toast;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GroceryList {
    private String name;
    private LinkedHashMap<String, Integer> productList = new LinkedHashMap<>();

    public GroceryList(String name) {
        this.name = name;
        if(name == null) { throw new NullPointerException("Name is null"); }
    }

    public GroceryList(String name, LinkedHashMap<String, Integer> productList) {
        this(name);
        if(productList == null) { throw new NullPointerException("Product list is null"); }
        this.productList = productList;
    }

    public void addItemToList(String fileName, String item, int quantity) {
        if(item == null) { throw new NullPointerException("Item is null");}
        Integer currentQuantity = productList.get(item);
        if (currentQuantity == null) {
            currentQuantity = 0;
        }
        currentQuantity += quantity;
        productList.put(item, currentQuantity);
    }

    //grocery list 1: item:quantity
    public String toString() {
        return name + ": " + productList.toString();
    }

    public String getName() {
        return name;
    }

    public LinkedHashMap<String, Integer> getProducts() {
        return productList;
    }
    //100 120 300 | 900
    //"E" "V" "A" | "Z"
    // 4  22   1  |  2
    //[1.........900]
    //hashmap.get("E")
}
