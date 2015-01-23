package com.example.darren.new_design;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.*;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;



public class Fragment_messenger extends Fragment{//} implements ListView.OnItemClickListener{

    ArrayList<Type_Contact> fetch = new ArrayList<>();
    Adapter_Contact adapter;
    ListView lv;
    TextView Person;



    public static MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<Type_SMS> mToDoTable;

    /**
     * Adapter to sync the items list with the view
     */
    private Adapter_SMS mAdapter;

    /**
     * EditText containing the "New To Do" text
     */
    private EditText mTextNewToDo;

    /**
     * Progress spinner to use for table operations
     */
    private ProgressBar mProgressBar;

    public static final String SENDER_ID = "403438380650"; // YOUR_GOOGLE_GCM_PROJECT_NUMBER
    private GoogleCloudMessaging gcm;
    private String regid;
    Button buttonAddToDo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.messenger, container, false);
        String[] values = new String[]{
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


        for (int i = 0; i < values.length; i++) {
            fetch.add(new Type_Contact(values[i], i + " New message"));
        }

        lv = (ListView) InputFragmentView.findViewById(R.id.listview);
        adapter = new Adapter_Contact(getActivity(), fetch);
        lv.setAdapter(adapter);
        //lv.setOnItemClickListener(this);

        Person = (TextView) InputFragmentView.findViewById(R.id.Person);


        mProgressBar = (ProgressBar) InputFragmentView.findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);

        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://androidnotification.azure-mobile.net/",
                    "NdsiKwErKQtudoOmwoLCGTWrocjkWL65",
                    getActivity()).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use
            mToDoTable = mClient.getTable(Type_SMS.class);

            mTextNewToDo = (EditText) InputFragmentView.findViewById(R.id.textNewToDo);
            buttonAddToDo = (Button)  InputFragmentView.findViewById(R.id.buttonAddToDo);
            buttonAddToDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem();
                }
            });

            // Create an adapter to bind the items with the view
            mAdapter = new Adapter_SMS(getActivity(), R.layout.row_sms);
            ListView listViewToDo = (ListView) InputFragmentView.findViewById(R.id.listViewToDo);
            listViewToDo.setAdapter(mAdapter);

            // Load the items from the Mobile Service
            refreshItemsFromTable();

            return InputFragmentView;
        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }
        NotificationsManager.handleNotifications(getActivity(), SENDER_ID, Notification_Handler.class);

        gcm = GoogleCloudMessaging.getInstance(getActivity());
        if (regid == null || "".equals(regid)) {
            registerInBackground();
        }

        return InputFragmentView;
    }

/*    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String person = fetch.get(position).getName();
        Person.setText(person);
        Toast.makeText(getActivity(), person, Toast.LENGTH_SHORT).show();
    }*/
  /*      @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }

            if (id == R.id.menu_refresh) {
                refreshItemsFromTable();
            }

            return super.onOptionsItemSelected(item);
        }
*/
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
    private void registerInBackground() {
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
                    mClient.getPush().register(regid, tags, new RegistrationCallback() {
                        @Override
                        public void onRegister(Registration registration, Exception exception) {
                            //if (exception != null) {
                            // handle error
                            //}
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
    }
    /**
     * Mark an item as completed
     *
     * @param item The item to mark
     */
    public void checkItem(Type_SMS item) {
        if (mClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        item.setComplete(true);

        mToDoTable.update(item, new TableOperationCallback<Type_SMS>() {
            public void onCompleted(Type_SMS entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    if (entity.isComplete()) {
                        mAdapter.remove(entity);
                    }
                } else {
                    createAndShowDialog(exception, "Error");
                }
            }

        });
    }

    // Add a new item

    public void addItem() {
        if (mClient == null) {
            return;
        }

        // Create a new item
        Type_SMS item = new Type_SMS();

        item.setText(mTextNewToDo.getText().toString());
        item.setComplete(false);

        // Insert the new item
        mToDoTable.insert(item, new TableOperationCallback<Type_SMS>() {
            public void onCompleted(Type_SMS entity, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    if (!entity.isComplete()) {
                        mAdapter.add(entity);
                    }
                } else {
                    createAndShowDialog(exception, "Error");
                }

            }
        });
        mTextNewToDo.setText("");
    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
        mToDoTable.where().field("complete").eq(val(false)).execute(new TableQueryCallback<Type_SMS>() {

            public void onCompleted(List<Type_SMS> result, int count, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    mAdapter.clear();

                    for (Type_SMS item : result) {
                        mAdapter.add(item);
                    }

                } else {
                    createAndShowDialog(exception, "Error");
                }
            }
        });
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception The exception to show in the dialog
     * @param title     The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if (exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message The dialog message
     * @param title   The dialog title
     */
    private void createAndShowDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    private class ProgressFilter implements ServiceFilter {

        @Override
        public void handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback,
                                  final ServiceFilterResponseCallback responseCallback) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
                @Override
                public void onResponse(ServiceFilterResponse response, Exception exception) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    if (responseCallback != null) responseCallback.onResponse(response, exception);
                }
            });
        }
    }
}

