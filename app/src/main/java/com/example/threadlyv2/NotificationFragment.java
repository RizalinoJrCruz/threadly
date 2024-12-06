package com.example.threadlyv2;

 import android.database.Cursor;
 import android.os.Bundle;

import androidx.fragment.app.Fragment;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 import java.util.ArrayList;
 import java.util.List;


public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotifAdapter notifAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(getActivity());

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.notifRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Fetch posts from the database
        List<Notif> notifList = fetchPostsFromDatabase();

        // Set the adapter
        notifAdapter = new NotifAdapter(getActivity(), notifList);
        recyclerView.setAdapter(notifAdapter);

        return view;
    }

    // Method to fetch posts from the database and convert them into Post objects
    private List<Notif> fetchPostsFromDatabase() {
        List<Notif> notifList = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllPosts();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String postTitle = cursor.getString(cursor.getColumnIndex("post_title"));


                // Add a Post object to the list
                notifList.add(new Notif(username,postTitle)); // Static likes/comments for now
            } while (cursor.moveToNext());
            cursor.close();
        }

        return notifList;
    }
}