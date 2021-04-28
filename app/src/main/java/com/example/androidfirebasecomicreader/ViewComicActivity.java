package com.example.androidfirebasecomicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidfirebasecomicreader.Adapter.MyLampAdapter;
import com.example.androidfirebasecomicreader.Adapter.MyViewPagerAdapter;
import com.example.androidfirebasecomicreader.Common.Common;
import com.example.androidfirebasecomicreader.Model.Chapter;
import com.example.androidfirebasecomicreader.Model.Script;
import com.mollin.yapi.YeelightDevice;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;

public class ViewComicActivity extends AppCompatActivity {

    ViewPager viewPager;
    TextView txt_chapter_name;
    View back, next;
    MyViewPagerAdapter adapter;
    MyLampAdapter lamp;
    Thread threadLamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comic);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.chapterIndex == 0)
                    Toast.makeText(ViewComicActivity.this, "You are reading first chapter", Toast.LENGTH_SHORT).show();
                else
                {
                    Common.chapterIndex--;
                    if(adapter != null) {
                        adapter.mediaPlayer.stop();
                        adapter.mediaPlayer.release();
                        adapter.mediaPlayer = null;
                        adapter = null;
                    }
                    if(lamp != null){
                        lamp.isWork = false;
                        //lamp.device = null;
                        //lamp = null;
                    }
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                    if(Common.scriptList != null)
                        startScript(Common.scriptList.get(Common.chapterIndex));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.chapterIndex == Common.chapterList.size() - 1) {
                    Toast.makeText(ViewComicActivity.this, "You are reading last chapter", Toast.LENGTH_SHORT).show();
                }
                else{
                    Common.chapterIndex++;
                    if(adapter != null) {
                        adapter.mediaPlayer.stop();
                        adapter.mediaPlayer.release();
                        adapter.mediaPlayer = null;
                        adapter = null;
                    }
                    if(lamp != null){
                        lamp.isWork = false;
                        //lamp.device = null;
                        //lamp = null;
                    }
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                    if(Common.scriptList != null)
                        startScript(Common.scriptList.get(Common.chapterIndex));
                }
            }
        });

        fetchLinks(Common.chapterSelected);
        if(Common.scriptSelected != null)
            startScript(Common.scriptSelected);

    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
        if(adapter != null) {
            adapter.mediaPlayer.stop();
            adapter.mediaPlayer.release();
            adapter.mediaPlayer = null;
        }
        if(lamp != null){
            lamp.isWork = false;
            //lamp.device = null;
            lamp = null;
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();

    }


    private void fetchLinks(Chapter chapter) {
        if(chapter.Links != null && chapter.Sound != null)
        {
            if(chapter.Links.size() > 0 && chapter.Sound.size() > 0 && adapter == null)
            {
                adapter = new MyViewPagerAdapter(ViewComicActivity.this, chapter.Links, chapter.Sound);
                viewPager.setAdapter(adapter);

                txt_chapter_name.setText(Common.formatString(chapter.Name));
            }
            else{
                Toast.makeText(this, "No image here", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            txt_chapter_name.setText(Common.formatString(chapter.Name));
            Toast.makeText(this, "This chapter is translating...", Toast.LENGTH_SHORT).show();
        }
    }

    private void startScript(Script script){
        if(script.Script != null) {
            if(lamp == null)
                lamp = new MyLampAdapter();
            else
                lamp.isWork = true;
            Toast.makeText(this, script.Script, Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lamp.setMorningMode();
                }
            }).start();
        }else {
            Toast.makeText(this, "No Script yet...", Toast.LENGTH_SHORT).show();
        }
    }
}