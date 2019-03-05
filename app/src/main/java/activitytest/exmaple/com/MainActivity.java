package activitytest.exmaple.com.thefirstshoes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    List<Fruit> datas=new ArrayList<Fruit>();
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleted",Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        initdata();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(datas);
        recyclerView.setAdapter(adapter);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });


    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initdata();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setQueryHint("请输入8位日期");
        searchView.setIconified(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this,SearchedActivity.class);
                intent.putExtra("Sdate",s);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                break;
            default:


        }
        return true;
    }
    //===============================================================================================
    //===============================================================================================
    //===============================================================================================
    //===============================================================================================
    //===============================================================================================
    private void initdata()
    {
        datas.clear();
        HttpConnect httpConnect=new HttpConnect("https://news-at.zhihu.com/api/4/news/latest");
        httpConnect.sendRequestWithHttpURLConnection(new HttpConnect.Callback() {
            @Override
            public void finish(String respone) {
                parseJSON(respone);//手解版

            }
        });
    }

    private void parseJSON(String respone) {
        try {
//            Log.d("bbbbb", respone);

            JSONObject jsonObject=new JSONObject(respone);

//            JSONObject jsonObjectOne=new JSONObject(jsonObject.getString("data"));


            JSONArray jsonArray=new JSONArray(jsonObject.getString("stories"));
//            Log.d("aaaaa", jsonObject.getString("top_stories"));
//
//            Log.d("aaaaa", "wocao"+jsonArray.length());
            JSONArray jsonArrayTwo=new JSONArray(jsonObject.getString("top_stories"));
//            Log.d("aaaaa", "wocao"+jsonArrayTwo.length());
            for (int i = 0; i < jsonArrayTwo.length(); i++) {
                JSONObject jsonObject1 = jsonArrayTwo.getJSONObject(i);
//                Log.d("wwwww", jsonObject1.getString("title"));

                Fruit data=new Fruit(jsonObject1.getString("title"),jsonObject1.getInt("id"),
                        jsonObject1.getString("image"));


                datas.add(data);
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                JSONArray jsonArray00 = new JSONArray(jsonObject2.getString("images"));
                Log.d("bbbbb","wocao"+jsonArray00.getString(0));

                Fruit data=new Fruit(jsonObject2.getString("title"),jsonObject2.getInt("id"),
                        jsonArray00.getString(0));
//                Log.d("aaaaa", jsonObject2.getString("images"));



                datas.add(data);
            }
//            for(int i = 0; i<datas.size();i++)
//            {
//                Log.d("ccccc", datas.get(i).getUrl());
//            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
// 另一种方式   public Bitmap getBitmap(String url) {
//        Bitmap bm = null;
//        try {
//            URL iconUrl = new URL(url);
//            URLConnection conn = iconUrl.openConnection();
//            HttpURLConnection http = (HttpURLConnection) conn;
//
//            int length = http.getContentLength();
//
//            conn.connect();
//            // 获得图像的字符流
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is, length);
//            bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();// 关闭流
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bm;
//    }


}
