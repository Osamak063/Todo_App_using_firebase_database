package com.example.osamakhalid.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int count=0;
    private EditText task;
    private List<Task> tasks= new ArrayList<Task>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    customAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase= FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();
        ListView listView= (ListView) findViewById(R.id.my_list);
        task= (EditText)  findViewById(R.id.addingTask);
        Button add= (Button) findViewById(R.id.add_button);
        adapter= new customAdapter(this,new ArrayList<Task>());
        listView.setAdapter(adapter);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task taskobj = dataSnapshot.getValue(Task.class);
                taskobj.setKey(dataSnapshot.getKey());
                adapter.setTask(taskobj);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        reference.addChildEventListener(childEventListener);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Task taskobj=new Task(task.getText().toString());
                reference.push().setValue(taskobj);
            }
        });

    }
}
