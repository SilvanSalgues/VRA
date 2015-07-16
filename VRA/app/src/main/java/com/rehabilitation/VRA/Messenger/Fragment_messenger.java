package com.rehabilitation.VRA.Messenger;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darren.VRA.R;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServicePreconditionFailedException;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.operations.RemoteTableOperationProcessor;
import com.microsoft.windowsazure.mobileservices.table.sync.operations.TableOperation;
import com.microsoft.windowsazure.mobileservices.table.sync.push.MobileServicePushCompletionResult;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.MobileServiceSyncHandler;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.MobileServiceSyncHandlerException;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import com.rehabilitation.VRA.Database.Database_Manager;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Fragment_messenger extends Fragment {

    /**
     * YOUR_GOOGLE_GCM_PROJECT_NUMBER
     */
    public static final String SENDER_ID = "403438380650";

    /**
     * Mobile Service Client reference
     */
    public static MobileServiceClient AzureClient;

    /**
     * Mobile Service Table used to access data (Type_SMS)
     */
    private MobileServiceSyncTable<Type_SMS> AzureTable;

    /**
     * The query used to pull data from the remote server
     */
    private Query mPullQuery;


    /**
     * EditText containing the "Message" text
     */
    EditText addSMStext;

    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar progressbar;


    Button addSMSbtn;
    SwipeRefreshLayout swipeLayout;
    Database_Manager db;

    LinearLayout Layout;
    ListView reponse_listview;
    public Context cont;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.messenger, container, false);
        cont = getActivity();
        db = new Database_Manager(cont);

        Layout =(LinearLayout) InputFragmentView.findViewById(R.id.boardposts_layout);

       /* swipeLayout = (SwipeRefreshLayout) InputFragmentView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItemsFromTable();
                swipeLayout.setRefreshing(false);
            }
        });
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

        progressbar = (ProgressBar) InputFragmentView.findViewById(R.id.loadingProgressBar);
        progressbar.setVisibility(ProgressBar.GONE); // Initialize the progress bar

        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            AzureClient = new MobileServiceClient(
                    "https://vra.azure-mobile.net/",
                    "zKFNGLoUcmEiZvLlnWRBiLqmosRqok52",
                    getActivity()).withFilter(new ProgressFilter());


            // Saves the query which will be used for pulling data
            mPullQuery = AzureClient.getTable(Type_SMS.class).where().field("complete").eq(false).orderBy("__createdAt", QueryOrder.Ascending);


            SQLiteLocalStore localStore = new SQLiteLocalStore(AzureClient.getContext(), "Type_SMS", null, 1);
            MobileServiceSyncHandler handler = new ConflictResolvingSyncHandler();
            MobileServiceSyncContext syncContext = AzureClient.getSyncContext();

            Map<String, ColumnDataType> tableDefinition = new HashMap<>();
            tableDefinition.put("id", ColumnDataType.String);
            tableDefinition.put("username", ColumnDataType.String);
            tableDefinition.put("text", ColumnDataType.String);
            tableDefinition.put("complete", ColumnDataType.Boolean);
            tableDefinition.put("__createdAt", ColumnDataType.Date);
            tableDefinition.put("__version", ColumnDataType.String);
            tableDefinition.put("attachedId", ColumnDataType.String);

            localStore.defineTable("Type_SMS", tableDefinition);
            syncContext.initialize(localStore, handler).get();

            // Get the Mobile Service Table instance to use
            AzureTable = AzureClient.getSyncTable(Type_SMS.class);

            addSMStext = (EditText) InputFragmentView.findViewById(R.id.addSMStext);
            addSMSbtn = (Button)  InputFragmentView.findViewById(R.id.addSMSbtn);
            addSMSbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If the Edit text for adding a message is not empty
                    if (!addSMStext.getText().toString().isEmpty()) {
                        addItem(addSMStext.getText().toString(), "");
                        addSMStext.setText("");
                    }
                }
            });


            NotificationsManager.handleNotifications(getActivity(), SENDER_ID, Notification_Handler.class);
            // Load the items from the Mobile Service
            refreshItemsFromTable();

        }
        catch (MalformedURLException e) {
            Log.d("" + new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            Log.d("" + new Exception("Unknown error: " + t.getMessage()), "Error");
        }

        return InputFragmentView;
    }

    public void removeItem(final Type_SMS item) {
        if (AzureClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        item.setComplete(true);


        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    AzureTable.update(item).get();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if (item.isComplete()) {
                                //adapter_sms.remove(item);
                                //updateSMScount(0);
                                refreshItemsFromTable();
                            }
                        }
                    });
                } catch (Exception e){
                    Log.d("" + e, "Error");
                }

                return null;
            }
        }.execute();
    }

    // Add a new item
    public void addItem(String Message, String AttachedId) {
        if (AzureClient == null) {
            return;
        }


        // Create a new item
        final Type_SMS item = new Type_SMS();

        Log.d("Device id", "" + Notification_Handler.getHandle());
        db.open();
        String username = db.getUsername(db.isUserLoggedIn());
        db.close();
        //item.setUsername(Notification_Handler.getHandle());
        item.setUsername(username);
        item.setattachedId(AttachedId);
        item.setText(Message);
        item.setComplete(false);

        // Insert the new item
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Type_SMS entity = AzureTable.insert(item).get();
                    if (!entity.isComplete()) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                //adapter_sms.add(entity);
                                Layout.removeAllViews();
                                //updateSMScount(0);
                            }
                        });
                    }
                } catch (Exception e){
                    Log.d("" + e, "Error");

                }

                return null;
            }
        }.execute();

        refreshItemsFromTable();
    }

    //Refresh the list with the items in the Mobile Service Table
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    AzureClient.getSyncContext().push().get();
                    AzureTable.pull(mPullQuery).get();
                    final MobileServiceList<Type_SMS> results = AzureTable.read(mPullQuery).get();
                    final List <Type_SMS> reponses = new ArrayList<>();


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //adapter_sms.clear();


                            for (Type_SMS item : results) {
                                if(item.getattachedId().isEmpty()){
                                    for(Type_SMS item2 : results){
                                        if(item2.getattachedId().equals(item.getId()))
                                        {
                                            reponses.add(item2);
                                        }
                                    }

                                    final Type_SMS original_post = item;

                                    View inflated = getActivity().getLayoutInflater().inflate(R.layout.boardpost, null);
                                    TextView smsText = (TextView) inflated.findViewById(R.id.smsText);
                                    smsText.setText(item.getText());

                                    TextView username = (TextView) inflated.findViewById(R.id.username);
                                    username.setText(original_post.getUsername());

                                    TextView time = (TextView) inflated.findViewById(R.id.time);

                                    //Time not working..
                                    //time.setText("" + original_post.getTimeSince());
                                    //Log.d("Time since", "" + original_post.getTimeSince());


                                    final EditText board_reply = (EditText) inflated.findViewById(R.id.board_reply);

                                    Button board_send_btn = (Button) inflated.findViewById(R.id.board_send_btn);
                                    board_send_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override

                                        public void onClick(View v) {
                                            if (!board_reply.getText().toString().isEmpty()) {
                                                Log.d("Row Text", "" + board_reply.getText());
                                                addItem(board_reply.getText().toString(), original_post.getId());
                                                board_reply.setText("");
                                            }
                                        }
                                    });


                                    // Create an adapter to bind the items with the view
                                    Adapter_SMS adapter_sms = new Adapter_SMS(getActivity(), R.layout.row_sms_sent, Fragment_messenger.this);

                                    reponse_listview = (ListView) inflated.findViewById(R.id.reponses);
                                    reponse_listview.setAdapter(adapter_sms);
                                    reponse_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            //here you can use the position to determine what message has been pressed
                                            Type_SMS currentItem = (Type_SMS) reponse_listview.getItemAtPosition(position);
                                            Toast.makeText(getActivity(), "Item " + currentItem, Toast.LENGTH_SHORT).show();

                                            AlertDialog diaBox = AskOption(currentItem);
                                            diaBox.show();
                                        }
                                    });


                                    for (int i = 0; i < reponses.size(); i++) {
                                        adapter_sms.add(reponses.get(i));
                                    }
                                    Layout.addView(inflated);

                                    for (Type_SMS reponse: reponses) {
                                        Log.d("Boardposts", "" + item.getText() + " & " + reponse.getText());
                                    }
                                    reponses.clear();
                                }
                            }
                            //updateSMScount(0);
                        }
                    });
                } catch (Exception exception){
                    Log.d("" + exception, "Error");

                }

                return null;
            }
        }.execute();

        // Scrolls the SMS List to the most recent message
        //SMS_listview.setSelection(adapter_sms.getCount() - 1);
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(
                ServiceFilterRequest request, NextServiceFilterCallback next) {

            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (progressbar != null) progressbar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> result = next.onNext(request);

            Futures.addCallback(result, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable exc) {
                    dismissProgressBar();
                }

                @Override
                public void onSuccess(ServiceFilterResponse resp) {
                    dismissProgressBar();
                }

                private void dismissProgressBar() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (progressbar != null) progressbar.setVisibility(ProgressBar.GONE);
                        }
                    });
                }
            });

            return result;
        }
    }

    private class ConflictResolvingSyncHandler implements MobileServiceSyncHandler {

        @Override
        public JsonObject executeTableOperation(
                final RemoteTableOperationProcessor processor, final TableOperation operation)
                throws MobileServiceSyncHandlerException {

            final JsonObject clientItem = processor.getItem().getAsJsonObject();

            MobileServicePreconditionFailedException ex = null;
            final JsonObject[] result = {null};
            try {
                result[0] = operation.accept(processor);
            } catch (MobileServicePreconditionFailedException e) {
                ex = e;
            } catch (Throwable e) {
                ex = (MobileServicePreconditionFailedException) e.getCause();
            }

            if (ex != null) {
                // A conflict was detected; let's make the client choose who "wins"
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cont);

                final CountDownLatch latch = new CountDownLatch(1);

                // set title
                alertDialogBuilder.setTitle("Conflict Detected");

                // set dialog message
                final MobileServicePreconditionFailedException finalEx = ex;
                alertDialogBuilder
                        .setMessage("Choose winner")
                        .setCancelable(false)
                        .setPositiveButton("Server",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, Server wins
                                JsonObject serverItem = (JsonObject) finalEx.getItem();

                                if (serverItem == null) {
                                    // Item not returned in the exception, retrieving it from the server
                                    try {
                                        serverItem = AzureClient.getTable(operation.getTableName()).lookUp(operation.getItemId()).get();
                                    } catch (Exception e) {
                                        try {
                                            throw new MobileServiceSyncHandlerException(e);
                                        } catch (MobileServiceSyncHandlerException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                                result[0] = serverItem;
                                latch.countDown();
                            }
                        })
                        .setNegativeButton("Client",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, Client wins
                                result[0] = clientItem;
                                latch.countDown();
                            }
                        });

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                });

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return result[0];

        }

        @Override
        public void onPushComplete(MobileServicePushCompletionResult result)
                throws MobileServiceSyncHandlerException {
        }
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

    // Method for displaying a Dialog when deleting message
    private AlertDialog AskOption( final Type_SMS currentItem ){
        return new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete Message")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        removeItem(currentItem);
                        dialog.dismiss();
                    }

                })


                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .create();
    }
}