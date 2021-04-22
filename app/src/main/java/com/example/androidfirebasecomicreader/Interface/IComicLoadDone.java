package com.example.androidfirebasecomicreader.Interface;

import com.example.androidfirebasecomicreader.Model.Book;

import java.util.List;

public interface IComicLoadDone {
    void onComicLoadDoneListener(List<Book> comicList);
}
