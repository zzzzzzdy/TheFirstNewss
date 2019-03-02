package activitytest.exmaple.com.thefirstshoes;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TalkActivity extends AppCompatActivity {
    List<Data> datas=new ArrayList<Data>();
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        Log.d("ababab",""+id);
        initData();
        recyclerView=findViewById(R.id.ta_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerAdapter=new RecyclerAdapter(datas,this);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
        GridLayoutManager manager = new GridLayoutManager(this,1);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(manager);
    }

    private void initData() {
        HttpConnect httpConnect=new HttpConnect("https://news-at.zhihu.com/api/4/story/"+id+"/short-comments");
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

            JSONArray jsonArray=new JSONArray(jsonObject.getString("comments"));


//            JSONArray jsonArray=new JSONArray(jsonObjectOne.getString("forecast"));
//            String ganmao = jsonObjectOne.getString("ganmao");
//            String wendu = jsonObjectOne.getString("wendu");
//            Message message = Message.obtain();
//            Bundle bundle = new Bundle();
//            bundle.putString("ganmao",ganmao);
//            bundle.putString("wendu",wendu);
//            message.setData(bundle);
//            handler.sendMessage(message);




            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                   Log.d("wwwww", jsonObject2.getString("date"));
//                    Log.d("rrrrr",jsonObject2.getString("date")+jsonObject2.getString("high")+jsonObject2.getString("fx"));
//                    Log.d("eeeee",jsonObject2.getString("date")+jsonObject2.getString("high")+jsonObject2.getString("fx")+
//                            jsonObject2.getString("low")+jsonObject2.getString("fl")+jsonObject2.getString("type"));
                Data data=new Data(jsonObject2.getString("avatar"),jsonObject2.getString("author")+": "
                        +jsonObject2.getString("content"));
                Log.d("ababab",jsonObject2.getString("avatar"));


                datas.add(data);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerAdapter.notifyDataSetChanged();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
