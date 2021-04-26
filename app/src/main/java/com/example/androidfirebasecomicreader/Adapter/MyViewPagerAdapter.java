package com.example.androidfirebasecomicreader.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.example.androidfirebasecomicreader.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter{
    Context context;
    List<String> imageLinks;
    List<String> soundLinks;
    LayoutInflater inflater;
    String previousSound = "";
    String currentSound;

    public MediaPlayer mediaPlayer = new MediaPlayer();

    public MyViewPagerAdapter(Context context, List<String> imageLinks, List<String> soundLinks){
        this.context = context;
        this.imageLinks = imageLinks;
        this.soundLinks = soundLinks;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return imageLinks.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View image_layout = inflater.inflate(R.layout.view_pager_item,container,false);
        PhotoView page_image = (PhotoView)image_layout.findViewById(R.id.page_image);
        Picasso.get().load(imageLinks.get(position)).into(page_image);

        container.addView(image_layout);
        return image_layout;
    }

    @Deprecated
    @Override
    public void setPrimaryItem(@NonNull View container, int position, @NonNull Object object) {
        currentSound = soundLinks.get(position);
        if(!previousSound.equals(currentSound)){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(currentSound);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(currentSound);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mediaPlayer.start();
            previousSound = currentSound;
        }
    }

}
