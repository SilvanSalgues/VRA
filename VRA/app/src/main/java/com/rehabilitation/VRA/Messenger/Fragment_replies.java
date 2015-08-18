package com.rehabilitation.VRA.Messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.darren.VRA.R;
import com.rehabilitation.VRA.Database.Database_Manager;

import java.util.List;

public class Fragment_replies extends Boardpost_Fragment {

    Database_Manager db;
    Activity activity;
    Adapter_Reply reply_adapter;
    ListView response_listview;
    SwipeRefreshLayout swipeLayout;

    Type_SMS maintopic;

    EditText addSMStext;
    Button addSMSbtn;

    private static final String DESCRIBABLE_KEY = "describable_key";

    public static Fragment_replies newInstance(Type_Boardpost boardpost){
        Fragment_replies fragment_replies = new Fragment_replies();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, boardpost);
        fragment_replies.setArguments(bundle);
        return fragment_replies;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.replies, container, false);
        activity = getActivity();

        db = new Database_Manager(activity);
        azure = new Azure(getActivity(), this);

        swipeLayout = (SwipeRefreshLayout) InputFragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reply_adapter.clear();
                azure.refreshItemsFromResponces(maintopic);
                swipeLayout.setRefreshing(false);
            }
        });
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        Type_Boardpost boardpost = (Type_Boardpost) getArguments().getSerializable(DESCRIBABLE_KEY);

        TextView smsText = (TextView)InputFragmentView.findViewById(R.id.smsText);
        TextView username = (TextView)InputFragmentView.findViewById(R.id.username);
        TextView time = (TextView)InputFragmentView.findViewById(R.id.time);

        reply_adapter = new Adapter_Reply(activity, R.layout.row_reply);
        response_listview = (ListView) InputFragmentView.findViewById(R.id.list_replies);
        response_listview.setAdapter(reply_adapter);
        response_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                Type_SMS currentItem = (Type_SMS) response_listview.getItemAtPosition(pos);

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

        if(boardpost != null) {
            maintopic = boardpost.getpost();
            //Toast.makeText(getActivity(), "Item " + maintopic.getText(), Toast.LENGTH_SHORT).show();

            smsText.setText(maintopic.getText());
            username.setText(maintopic.getUsername());
            time.setText(maintopic.getTimeSince());

            List<Type_SMS> responses = boardpost.getresponses();

            for (Type_SMS response: responses) {
                reply_adapter.add(response);
            }

            addSMStext = (EditText) InputFragmentView.findViewById(R.id.reply);
            addSMSbtn = (Button)  InputFragmentView.findViewById(R.id.send_btn);
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
                        item.setattachedId(maintopic.getId());
                        item.setText(addSMStext.getText().toString());
                        item.setComplete(false);

                        azure.addItem(item);
                        addSMStext.setText("");
                        reply_adapter.clear();
                        azure.refreshItemsFromResponces(maintopic);
                    }
                }
            });

        }
        return InputFragmentView;
    }

    @Override
    public void add_boardpost(Type_SMS Topic){
        reply_adapter.add(Topic);
        //Log.d("Fragment replies", " Add_boardpost");
    }

    @Override
    public void remove_boardpost(Type_SMS Topic){
        reply_adapter.remove(Topic);
        //Log.d("Fragment replies", " remove_boardpost");
    }
}
