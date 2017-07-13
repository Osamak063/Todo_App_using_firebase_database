package com.example.osamakhalid.todo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by Osama Khalid on 7/11/2017.
 */

public class customAdapter extends ArrayAdapter<Task>{
    private List<Task> tasks= new ArrayList<Task>();
    DatabaseReference databaseReference;
    public customAdapter(@NonNull Context context, List<Task> tasks) {
        super(context,R.layout.task_items,tasks);
        this.tasks=tasks;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = convertView;
        if (customView == null) {
            customView = LayoutInflater.from(getContext()).inflate(R.layout.task_items, parent, false);
        }
        final Task task = getItem(position);
        TextView name = (TextView) customView.findViewById(R.id.taskname);
        ImageButton delete = (ImageButton) customView.findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Task task1 = getItem(position);
                String title = task1.gettaskname();
                  databaseReference = FirebaseDatabase.getInstance().getReference();
                Query query = databaseReference.orderByChild("taskname").equalTo(title);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        databaseReference.child(task1.getKey()).removeValue();
                        tasks.remove(task1);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        name.setText(task.gettaskname());
        return customView;

    }
    public void setTask(Task data) {
        tasks.add(data);
        notifyDataSetChanged();
    }

}