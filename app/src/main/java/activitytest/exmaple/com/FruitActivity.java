package activitytest.exmaple.com.thefirstshoes;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


public class FruitActivity extends AppCompatActivity {
    public static  final String FRUIT_ID = "fruit_id";
    public static  final String FRUIT_NAME = "fruit_name";
    public static  final String FRUIT_IMGE = "fruit_image";
    public int fruitId;
    public String fruitContent;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            fruitContent = msg.getData().getString("fruitContent");

            TextView fruitContentText = findViewById(R.id.fruit_content_text);
            fruitContentText.setText(fruitContent);

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);//标题
        String fruitImage = intent.getStringExtra(FRUIT_IMGE);//url
        fruitId = intent.getIntExtra(FRUIT_ID,0);//id
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.f_button);
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);
//        TextView fruitContentText = findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(fruitName);
        Glide.with(this).load(fruitImage).into(fruitImageView);
        initFruitContent();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FruitActivity.this,TalkActivity.class);
                intent1.putExtra("id",fruitId);
                startActivity(intent1);

            }
        });
//        fruitContentText.setText(fruitContent);


    }
    private void initFruitContent()
    {

        Log.d("ccccc","a"+fruitId);
        HttpConnect httpConnect=new HttpConnect("https://news-at.zhihu.com/api/4/news/"+fruitId);
        httpConnect.sendRequestWithHttpURLConnection(new HttpConnect.Callback() {
            @Override
            public void finish(String respone) {
                parseJSON(respone);//手解版

            }
        });
    }
    private void parseJSON(String respone) {
        try {
           Log.d("bbbbb", respone);

            JSONObject jsonObject=new JSONObject(respone);
            String abc = jsonObject.getString("body");
//            Log.d("ccccc", fruitContent);
            fruitContent = Html2Text(abc);
//            Log.d("abcabc","a"+abc);
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("fruitContent",fruitContent);

            message.setData(bundle);
            handler.sendMessage(message);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {System.err.println("Html2Text: " + e.getMessage()); }
        //剔除空格行
        textStr=textStr.replaceAll("[ ]+", " ");
        textStr=textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        return textStr;// 返回文本字符串
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
