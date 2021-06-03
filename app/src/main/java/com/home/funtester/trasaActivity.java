package com.home.funtester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class trasaActivity extends AppCompatActivity {

    private ArrayList<itemData_trasa> arrayList;
    private RecyclerView rvtrasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasa);
        rvtrasa = findViewById(R.id.rv_trasa);
        rvtrasa.setHasFixedSize(true);
        rvtrasa.setLayoutManager(new LinearLayoutManager(this));
        JonsAsyncTask jonsAsyncTask=new JonsAsyncTask();
        jonsAsyncTask.execute("http://atm201605.appspot.com/h");
    }

    public class JonsAsyncTask extends android.os.AsyncTask<String,Void,String>{


        private StringBuffer sb;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayList = new ArrayList<>();
            try {
                JSONArray jsonArray=new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String account =jsonObject.getString("account");
                    String date =jsonObject.getString("date");
                    int amount=jsonObject.getInt("amount");
                    int type=jsonObject.getInt("type");
                    arrayList.add(new itemData_trasa(account,date,amount,type));
                }
                RecycViewAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        private void RecycViewAdapter() {

            Adapter adapter =new Adapter();
            rvtrasa.setAdapter(adapter);
        }


        @Override
        protected String doInBackground(String... strings) {
            try {
                sb = new StringBuffer();
                URL url=new URL(strings[0]);
                InputStream inputStream=url.openStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
                String n= br.readLine();
                while ( n != null){
                    sb.append(n);
                    n= br.readLine();
                }
                Log.d("main", "doInBackground: "+ sb);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return sb.toString();
        }
    }
    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHoder>{


        @Override
        public ViewHoder onCreateViewHolder( ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(trasaActivity.this).inflate(R.layout.layout_item_trasa,parent,false);
            return new ViewHoder(view);
        }

        @Override
        public void onBindViewHolder(trasaActivity.Adapter.ViewHoder holder, int position) {
            itemData_trasa itemData_trasa=arrayList.get(position);
            holder.account.setText(itemData_trasa.getAccount());
            holder.date.setText(itemData_trasa.getDate());
            holder.amount.setText(itemData_trasa.getAmount()+"");
            holder.type.setText(itemData_trasa.getType()+"");

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHoder extends RecyclerView.ViewHolder{
            TextView account;
            TextView date;
            TextView amount;
            TextView type;

            public ViewHoder( View itemView) {
                super(itemView);
                account=itemView.findViewById(R.id.tv_account);
                date=itemView.findViewById(R.id.tv_date);
                amount=itemView.findViewById(R.id.tv_amount);
                type=itemView.findViewById(R.id.tv_type);
            }
        }
    }
}