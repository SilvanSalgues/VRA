package com.rehabilitation.VRA.Messenger;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.darren.VRA.R;
import com.rehabilitation.VRA.Database.Database_Manager;

public class Fragment_messenger extends Boardpost_Fragment {

    EditText addSMStext;
    Button addSMSbtn;


    SwipeRefreshLayout swipeLayout;
    Database_Manager db;
    ListView boardpost_listview ;
    public Context cont;

    Adapter_MainPost boardpost_adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.messenger, container, false);
        cont = getActivity();
        db = new Database_Manager(cont);

        // Load the items from the Mobile Service
        azure = new Azure(getActivity(), this);
        azure.refreshItemsFromTable();

        swipeLayout = (SwipeRefreshLayout) InputFragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_boardposts();
                swipeLayout.setRefreshing(false);
            }
        });
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        progressbar = (ProgressBar) InputFragmentView.findViewById(R.id.loadingProgressBar);
        progressbar.setVisibility(ProgressBar.GONE); // Initialize the progress bar

        // Create an adapter to bind the items with the view
        boardpost_adapter = new Adapter_MainPost(getActivity(), R.layout.row_maintopic);
        boardpost_listview = (ListView) InputFragmentView.findViewById(R.id.listboardposts);
        boardpost_listview.setAdapter(boardpost_adapter);
        boardpost_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                //here you can use the position to determine what message has been pressed
                Type_SMS currentItem = (Type_SMS) boardpost_listview.getItemAtPosition(pos);
                //Toast.makeText(getActivity(), "Item " + currentItem, Toast.LENGTH_SHORT).show();

                azure.getTopicReplies(currentItem);

            }
        });
        boardpost_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                Type_SMS currentItem = (Type_SMS) boardpost_listview.getItemAtPosition(pos);

                db.open();
                String username = db.getUsername(db.isUserLoggedIn());
                db.close();

                if(username.equals(currentItem.getUsername())) {
                    AlertDialog diaBox = azure.AskOption(currentItem);
                    diaBox.show();
                }

                return true;
            }
        });


            addSMStext = (EditText) InputFragmentView.findViewById(R.id.addSMStext);
            addSMSbtn = (Button)  InputFragmentView.findViewById(R.id.addSMSbtn);
            addSMSbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If the Edit text for adding a message is not empty
                    if (!addSMStext.getText().toString().isEmpty()) {

                        // Create a new item
                        final Type_SMS item = new Type_SMS();

                        db.open();
                        String username = db.getUsername(db.isUserLoggedIn());
                        db.close();

                        item.setUsername(username);
                        item.setattachedId("");
                        item.setText(addSMStext.getText().toString());
                        item.setComplete(false);

                        azure.addItem(item);
                        addSMStext.setText("");
                        refresh_boardposts();
                    }
                }
            });

        return InputFragmentView;
    }

    @Override
    public void addBoardPost(Type_SMS Topic){
        boardpost_adapter.add(Topic);
        //Log.d("Fragment Messenger"," Add_boardpost");
    }

    @Override
    public void removeBoardPost(Type_SMS Topic){
        boardpost_adapter.remove(Topic);
        //Log.d("Fragment Messenger", " removeBoardPost");
    }

    public void refresh_boardposts(){

            boardpost_adapter.clear();
            azure.refreshItemsFromTable();
    }

    // Update SMS count on contact list
//    private void updateSMScount(int index){
//       View v = Contact_listview.getChildAt(index -
//                Contact_listview.getFirstVisiblePosition());
//
//        if (v == null) {
//            return;
//        }
//        TextView NumberOfSMS = (TextView) v.findViewById(R.id.NumberOfSMS);
//        int countSMS = adapter_sms.getCount();
//        String countToString;
//        if (countSMS == 0) {
//            countToString = "No Messages";
//        }
//        else if (countSMS == 1) {
//            countToString ="1 Message";
//        }
//        else{
//            countToString = countSMS + " Messages";
//        }
//        NumberOfSMS.setText(countToString);
//        //fetch.set(0, new Type_Contact(contact_name[index], countToString, contact_icon[index]));
//    }


}