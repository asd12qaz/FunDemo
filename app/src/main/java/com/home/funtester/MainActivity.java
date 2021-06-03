package com.home.funtester;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.funtester.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    boolean login=false;
    public static int LOGIN_OK;
    private RecyclerView recyclerView;
    private ArrayList<itemData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if(!login){
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        LOGIN_OK=100;
        findViews();
    }

    private void findViews() {
        arrayList = new ArrayList<>();
        String[] name= getResources().getStringArray(R.array.name);
        arrayList.add(new itemData(name[0],R.drawable.func_transaction));
        arrayList.add(new itemData(name[1],R.drawable.func_balance));
        arrayList.add(new itemData(name[2],R.drawable.func_finance));
        arrayList.add(new itemData(name[3],R.drawable.func_contacts));
        arrayList.add(new itemData(name[4],R.drawable.icon_map));
        arrayList.add(new itemData(name[5],R.drawable.icon_camera));
        arrayList.add(new itemData(name[6],R.drawable.func_exit));


        recyclerView = findViewById(R.id.rv_mainActivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        Adapter adapter=new Adapter();
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if(LOGIN_OK!=50){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder( MainActivity.Adapter.ViewHolder holder, int position) {
            itemData itemData=arrayList.get(position);
            holder.name.setText(itemData.getName());
            holder.icon.setImageResource(itemData.getIcon());
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClick(itemData);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            ImageView icon;
            public ViewHolder( View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.tv_name);
                icon=itemView.findViewById(R.id.im_icom);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Intent intent2=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent2);
        }
    }

    private void itemOnClick(itemData itemData) {
        switch (itemData.getIcon()){
            case (int)R.drawable.func_transaction:
                Log.d("main", "itemOnClick:  func_transaction");
                Intent intent =new Intent(this,trasaActivity.class);
                startActivity(intent);
                break;
            case (int)R.drawable.func_balance:
                Log.d("main", "itemOnClick:  func_balance");
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("To be developed")
                        .setPositiveButton("ok",null)
                        .show();
                break;
            case (int)R.drawable.func_finance:
                Log.d("main", "itemOnClick:  func_finance");
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("To be developed")
                        .setPositiveButton("ok",null)
                        .show();
                break;

            case (int)R.drawable.func_contacts:
                Log.d("main", "itemOnClick:  func_contacts");
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("To be developed")
                        .setPositiveButton("ok",null)
                        .show();
                break;
            case (int)R.drawable.icon_map:
                Log.d("main", "itemOnClick:  icon_map");
                Intent intent1=new Intent(this,MapsActivity.class);
                startActivity(intent1);
                break;
            case (int)R.drawable.icon_camera:
                int aa=ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                if(aa== PackageManager.PERMISSION_GRANTED){
                    Log.d("main", "itemOnClick:  icon_camera");
                    Intent intent2=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent2);
                }else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},0);
                }


                break;
            case (int)R.drawable.func_exit:
                Log.d("main", "itemOnClick:  func_exit");
                finish();
                break;
        }

            ;

    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}