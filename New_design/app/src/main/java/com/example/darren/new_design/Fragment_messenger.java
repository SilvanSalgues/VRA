package com.example.darren.new_design;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.notifications.Registration;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Fragment_messenger extends Fragment {//} implements ListView.OnItemClickListener{

    ArrayList<Type_Contact> fetch = new ArrayList<>();

    Adapter_Contact adapter_contact;
    Adapter_SMS adapter_sms;    // Adapter to sync the items list with the view

    ListView Contact_listview;
    TextView Person;

    EditText addSMStext;

    ProgressBar progressbar;    // Progress spinner to use for table operations

    public MobileServiceClient AzureClient;
    //public static MobileServiceClient getClient() {return AzureClient;}

    MobileServiceTable<Type_SMS> AzureTable; // Mobile Service Table used to access data

    String SENDER_ID = "403438380650"; // YOUR_GOOGLE_GCM_PROJECT_NUMBER
    GoogleCloudMessaging gcm;
    String regid;
    //String PhoneGcm = "APA91bGXWR-PTuQOVZXyohDheXj1TkLYMdABhHCpxrKndFNtSancH-vWadMffXVD38-bLvJbxdZ0OifrzK8v1emhHh1Isfh1zalrCgQJElS5xsgkEhSMYvZQAh3p5crZ4lVVNXX5voEWiCnE9B_IernkEesflt0vJ7FSeD01tWu5f7SHEwqO5xk";

    Button addSMSbtn;
    ListView SMS_listview;

    String[] contact_name;

    ImageView icon_person;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.messenger, container, false);
        contact_name = new String[]{
                "Bill Gates",
                "Warren Buffett",
                "Larry Ellison",
                "Christy Walton",
                "Jim Walton",
                "Charles Koch",
                "David Koch",
                "Alice Walton",
                "S. Robson Walton",
                "Michael Bloomberg",
                "Mark Zuckerberg",
        };

        Random r = new Random();
        for (String aContact_name : contact_name) {

            int rand = r.nextInt(10);

            if (rand == 0) {
                fetch.add(new Type_Contact(aContact_name, "No new messages"));
            } else if (rand == 1) {
                fetch.add(new Type_Contact(aContact_name, rand + " New message"));
            } else {
                fetch.add(new Type_Contact(aContact_name, rand + " New messages"));
            }
        }

        Person = (TextView) InputFragmentView.findViewById(R.id.Person);
        icon_person = (ImageView) InputFragmentView.findViewById(R.id.icon_person);

        if (contact_name.length > 0)
        {
            Person.setText(contact_name[0]);
        }

        adapter_contact = new Adapter_Contact(getActivity(), fetch);
        Contact_listview = (ListView) InputFragmentView.findViewById(R.id.listview);
        Contact_listview.setAdapter(adapter_contact);
        Contact_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //here you can use the position to determine what persons messages to display
                Person.setText(fetch.get(position).getName());
                icon_person.setBackgroundResource(R.drawable.profile);
            }
        });

        progressbar = (ProgressBar) InputFragmentView.findViewById(R.id.loadingProgressBar);
        progressbar.setVisibility(ProgressBar.GONE); // Initialize the progress bar

        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            AzureClient = new MobileServiceClient(
                    "https://androidnotification.azure-mobile.net/",
                    "NdsiKwErKQtudoOmwoLCGTWrocjkWL65",
                    getActivity()).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use
            AzureTable = AzureClient.getTable(Type_SMS.class);

            addSMStext = (EditText) InputFragmentView.findViewById(R.id.addSMStext);
            addSMSbtn = (Button)  InputFragmentView.findViewById(R.id.addSMSbtn);
            addSMSbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem();
                }
            });

            // Create an adapter to bind the items with the view
            adapter_sms = new Adapter_SMS(getActivity(), R.layout.row_sms);
            SMS_listview = (ListView) InputFragmentView.findViewById(R.id.SMS_listview);
            SMS_listview.setAdapter(adapter_sms);
            SMS_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //here you can use the position to determine what message has been pressed
                    //Toast.makeText(getActivity(),"Position " +  position,Toast.LENGTH_SHORT).show();

                    Type_SMS currentItem = (Type_SMS) SMS_listview.getItemAtPosition(position);
                    Toast.makeText(getActivity(), "Item " + currentItem, Toast.LENGTH_SHORT).show();

                    AlertDialog diaBox = AskOption(currentItem);
                    diaBox.show();
                }
            });


            // Load the items from the Mobile Service
            refreshItemsFromTable();

            NotificationsManager.handleNotifications(getActivity(), SENDER_ID, Notification_Handler.class);

            //gcm = GoogleCloudMessaging.getInstance(getActivity());
            //if (regid == null || "".equals(regid)) {
            //    registerInBackground();
            //}

            }
            catch (MalformedURLException e) {

                //There was an error creating the Mobile Service. Verify the URL
                Log.d("Verify the URL", "error creating the Mobile Service ");
        }


        return InputFragmentView;
    }

    /**
     * Registers mobile services client to receive GCM push notifications
     * @param gcmRegistrationId The Google Cloud Messaging session Id returned
     * by the call to GoogleCloudMessaging.register in NotificationsManager.handleNotifications
     */
    public void registerForPush(String gcmRegistrationId){
        String [] tags = {null};
        ListenableFuture<Registration> reg = AzureClient.getPush().register(gcmRegistrationId, tags);

        Futures.addCallback(reg, new FutureCallback<Registration>() {
            @Override
            public void onFailure(Throwable exc) {
                Log.d("" + exc, "Error");
            }
            @Override
            public void onSuccess(Registration reg) {
                Log.d("Registration", reg.getRegistrationId() + " resistered");
            }
        });
    }






        /**
         * Registers mobile services client to receive GCM push notifications
         *
         * @param gcmRegistrationId The Google Cloud Messaging session Id returned
         *                          by the call to GoogleCloudMessaging.register in NotificationsManager.handleNotifications
         */
        //public void registerForPush(String gcmRegistrationId) {
        //	String[] tags = {null};
        //}

        /**
         * Registers the application with GCM servers asynchronously.
         * <p/>
         * Stores the registration ID and app versionCode in the application's
         * shared preferences.
         */
    /*private void registerInBackground() {
        new AsyncTask() {
            //@Override
            //protected void onPostExecute(Object o) {
            //	super.onPostExecute(o);
            //}

            @Override
            protected Object doInBackground(Object[] objects) {
                String msg;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getActivity());
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    String [] tags = {null};
                    AzureClient.getPush().register(regid, tags, new RegistrationCallback() {
                        @Override
                        public void onRegister(Registration registration, Exception exception) {
                            if (exception != null) {
                            // handle error
                            }
                        }});

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }
        };
    } */

    public void checkItem(final Type_SMS item) {
        if (AzureClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        item.setComplete(true);


        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Type_SMS entity = AzureTable.update(item).get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //if (entity.isComplete()) {
                                adapter_sms.remove(entity);
                            //}
                        }
                    });
                } catch (Exception exception){
                    Log.d("" + exception, "Error");
                }

                return null;
            }
        }.execute();
    }

    // Add a new item
    public void addItem() {
        if (AzureClient == null) {
            return;
        }

        // Create a new item
        final Type_SMS item = new Type_SMS();

        Log.d("Device id", "" + Notification_Handler.getHandle());
        item.setUser(Notification_Handler.getHandle());//regid);
        item.setText(addSMStext.getText().toString());
        item.setComplete(false);

        // Insert the new item
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final Type_SMS entity = AzureTable.insert(item).get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!entity.isComplete()){
                                adapter_sms.add(entity);
                            }
                        }
                    });
                } catch (Exception exception){
                    Log.d("" + exception, "Error");

                }

                return null;
            }
        }.execute();

        addSMStext.setText("");
    }


    //Refresh the list with the items in the Mobile Service Table
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<Type_SMS> results =
                            AzureTable.where().field("complete").
                                    eq(false).execute().get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter_sms.clear();

                            for (Type_SMS item : results) {
                                adapter_sms.add(item);
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

    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();


            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (progressbar != null) progressbar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (progressbar != null) progressbar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }

    // Method for displaying a Dialog when deleting message
    private AlertDialog AskOption( final Type_SMS currentItem ){
        return new AlertDialog.Builder(getActivity())
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete Message")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        checkItem(currentItem);
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

