package activitytest.exmaple.com.thefirstshoes;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context mContext;
    private List<Fruit> mFruitList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        TextView upupNumber;
        TextView talksNumber;

        public ViewHolder(@NonNull View view) {
            super(view);
            cardView = (CardView) view;
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.fruit_name);
            upupNumber = view.findViewById(R.id.upup_number);
            talksNumber = view.findViewById(R.id.talks_number);
        }


    }

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext==null){
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
//        Log.d("aaaaaa","a");
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Log.d("aaaaaa","ab");
                int position =holder.getLayoutPosition();
                //            Log.d("bbbbbb","ab"+position);
                Fruit fruit = mFruitList.get(position);
//                Log.d("aaaaaa","a");
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getTitle());
                intent.putExtra(FruitActivity.FRUIT_IMGE,fruit.getUrl());
                intent.putExtra(FruitActivity.FRUIT_ID,fruit.getImageId());
//                Log.d("aaaaaa",fruit.getTitle()+fruit.getUrl());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getTitle());
        Glide.with(mContext).load(fruit.getUrl()).into(holder.fruitImage);
        Random random = new Random();
        int index = random.nextInt(100);
        int indexTwo = random.nextInt(50);
       holder.upupNumber.setText(" "+index);
       holder.talksNumber.setText(" "+indexTwo);


    }


    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

}
