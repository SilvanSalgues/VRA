package com.rehabilitation.VRA.Messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

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

public class Azure {

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

    MobileServiceList<Type_SMS> results;

    Activity activity;
    List<Type_SMS> mainTopics;
    Boardpost_Fragment boardpost_fragment;
    Database_Manager db;

    public Azure(Activity activity, Boardpost_Fragment boardpost_fragment) {

        this.boardpost_fragment = boardpost_fragment;
        this.activity = activity;
        mainTopics = new ArrayList<>();
        db = new Database_Manager(activity);

        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            AzureClient = new MobileServiceClient(
                    "https://vra.azure-mobile.net/",
                    "zKFNGLoUcmEiZvLlnWRBiLqmosRqok52",
                    activity).withFilter(new ProgressFilter());


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


            NotificationsManager.handleNotifications(activity, SENDER_ID, Notification_Handler.class);


        }  catch (MalformedURLException e) {
            Log.d("" + new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            Log.d("" + new Exception("Unknown error: " + t.getMessage()), "Error");
        }
    }


    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(
                ServiceFilterRequest request, NextServiceFilterCallback next) {

            boardpost_fragment.runProgressBar();

            ListenableFuture<ServiceFilterResponse> result = next.onNext(request);

            Futures.addCallback(result, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable exc) {

                    boardpost_fragment.dismissProgressBar();
                }

                @Override
                public void onSuccess(ServiceFilterResponse resp) {

                    boardpost_fragment.dismissProgressBar();
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
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

                final CountDownLatch latch = new CountDownLatch(1);

                // set title
                alertDialogBuilder.setTitle("Conflict Detected");

                // set dialog message
                final MobileServicePreconditionFailedException finalEx = ex;
                alertDialogBuilder
                        .setMessage("Choose winner")
                        .setCancelable(false)
                        .setPositiveButton("Server", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
                        .setNegativeButton("Client", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, Client wins
                                result[0] = clientItem;
                                latch.countDown();
                            }
                        });

                activity.runOnUiThread(new Runnable() {
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

    public void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    AzureClient.getSyncContext().push().get();
                    AzureTable.pull(mPullQuery).get();
                    results = AzureTable.read(mPullQuery).get();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (Type_SMS item : results) {
                                if (item.getattachedId().isEmpty()) {
                                    //Log.d("main topic", "" + item);
                                    boardpost_fragment.add_boardpost(item);
                                }
                            }
                        }
                    });
                } catch (Exception exception){
                    Log.d("" + exception, "Error");

                }
                return null;
            }
        }.execute();
    }

    public void refreshItemsFromResponces(final Type_SMS topic) {


        // Get the items that weren't marked as completed and add them in the
        // adapter
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    AzureClient.getSyncContext().push().get();
                    AzureTable.pull(mPullQuery).get();
                    results = AzureTable.read(mPullQuery).get();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (Type_SMS item : results) {
                                if (item.getattachedId().equals(topic.getId())) {
                                    //wLog.d("main topic", "" + item);
                                    boardpost_fragment.add_boardpost(item);
                                }
                            }
                        }
                    });
                } catch (Exception exception){
                    Log.d("" + exception, "Error");

                }
                return null;
            }
        }.execute();
    }

    public void getTopicReplies(Type_SMS topic) {

        final Type_SMS currentItem = topic;
        // Get the items that weren't marked as completed and add them in the
        // adapter
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    AzureClient.getSyncContext().push().get();
                    AzureTable.pull(mPullQuery).get();
                    results = AzureTable.read(mPullQuery).get();
                    final List<Type_SMS> reponses = new ArrayList<>();

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (Type_SMS item : results) {
                                if (item.getattachedId().equals(currentItem.getId())) {
                                    Log.d("main topic", "" + item);
                                    reponses.add(item);
                                }
                            }
                        }
                    });

                    boardpost_fragment.loadFragment_replies(currentItem, reponses);
                } catch (Exception exception){
                    Log.d("" + exception, "Error");

                }
                return null;
            }
        }.execute();
    }

    public void removeItem(final Type_SMS currentItem) {
        if (AzureClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        currentItem.setComplete(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    AzureTable.update(currentItem).get();
                } catch (Exception e){
                    Log.d("" + e, "Error");
                }

                return null;
            }
        }.execute();
    }


    // Add a new item
    public void addItem(final Type_SMS item) {
        if (AzureClient == null) {
            return;
        }

        // Insert the new item
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    AzureTable.insert(item).get();
                } catch (Exception e){
                    Log.d("" + e, "Error");

                }
                return null;
            }
        }.execute();
    }

    // Method for displaying a Dialog when deleting message
    public AlertDialog AskOption( final Type_SMS currentItem ){
        return new AlertDialog.Builder(activity)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete Message")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        removeItem(currentItem);
                        boardpost_fragment.remove_boardpost(currentItem);
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