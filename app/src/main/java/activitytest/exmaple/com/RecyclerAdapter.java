package activitytest.exmaple.com.thefirstshoes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecViewHolder> {
    private List<Data> datas = new ArrayList<Data>();
    private Context mcontext;
    public RecyclerAdapter(List<Data> datas, Context context) {
        this.datas = datas;
        this.mcontext = context;
    }
    @NonNull
    @Override
    public RecyclerAdapter.RecViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.ta_item, viewGroup,false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecViewHolder recViewHolder, int i) {
        Data data = datas.get(i);
        Glide.with(mcontext).load(data.getUrl()).into(recViewHolder.iamge);
        recViewHolder.textView.setText(data.getTalks());
        Random random = new Random();
        int index = random.nextInt(100);
        int indexTwo = random.nextInt(50);
        recViewHolder.upupNumber.setText(" "+index);
        recViewHolder.talksNumber.setText(" "+indexTwo);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView iamge;
        TextView textView;
        TextView upupNumber;
        TextView talksNumber;
        public RecViewHolder(View itemview) {
            super(itemview);
            cardView = (CardView) itemView;
            iamge = itemview.findViewById(R.id.ta_image);
            textView = itemview.findViewById(R.id.ta_text);
            upupNumber = itemview.findViewById(R.id.ta_upup_number);
            talksNumber = itemview.findViewById(R.id.ta_talks_number);

        }
    }
}
