package com.example.darren.new_design;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.Registration;
import com.microsoft.windowsazure.mobileservices.RegistrationCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.val;

public class Fragment_messenger extends Fragment{//} implements ListView.OnItemClickListener{

    ArrayList<Type_Contact> fetch = new ArrayList<>();

    Adapter_Contact adapter_contact;
    Adapter_SMS adapter_sms;    // Adapter to sync the items list with the view

    ListView Contact_listview;
    TextView Person;

    EditText addSMStext;

    ProgressBar progressbar;    // Progress spinner to use for table operations

    MobileServiceClient AzureClient;
    MobileServiceTable<Type_SMS> AzureTable; // Mobile Service Table used to access data

    String SENDER_ID = "403438380650"; // YOUR_GOOGLE_GCM_PROJECT_NUMBER
    GoogleCloudMessaging gcm;
    String regid;
    Button addSMSbtn;
    ListView SMS_listview;

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

        adapter_contact = new Adapter_Contact(getActivity(), fetch);
        Contact_listview = (ListView) InputFragmentView.findViewById(R.id.listview);
        Contact_listview.setAdapter(adapter_contact);
        Contact_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //here you can use the position to determine what checkbox to check
                //this assumes that you have an array of your checkboxes as well. called checkbox
                //String person = fetch.get(position).getName();
                //Person.setText(person);
                Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
            }
        });

        Person = (TextView) InputFragmentView.findViewById(R.id.Person);
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

            // Load the items from the Mobile Service
            refreshItemsFromTable();

            NotificationsManager.handleNotifications(getActivity(), SENDER_ID, Notification_Handler.class);

            gcm = GoogleCloudMessaging.getInstance(getActivity());
            if (regid == null || "".equals(regid)) {
                registerInBackground();
            }

            }
            catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
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
    }
    /**
     * Mark an item as completed
     *
     * @param item The item to mark
     */
    public void checkItem(Type_SMS item) {
        if (AzureClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        item.setComplete(true);

        AzureTable.update(item, new TableOperationCallback<Type_SMS>() {
            public void onCompleted(Type_SMS entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    if (entity.isComplete()) {
                        adapter_sms.remove(entity);
                    }
                } else {
                    createAndShowDialog(exception, "Error");
                }
            }

        });
    }

    // Add a new item

    public void addItem() {
        if (AzureClient == null) {
            return;
        }

        // Create a new item
        Type_SMS item = new Type_SMS();

        item.setText(addSMStext.getText().toString());
        item.setComplete(false);

        // Insert the new item
        AzureTable.insert(item, new TableOperationCallback<Type_SMS>() {
            public void onCompleted(Type_SMS entity, Exception exception, ServiceFilterResponse response) {

                if (exception == null) {
                    if (!entity.isComplete()) {
                        adapter_sms.add(entity);
                    }
                } else {
                    createAndShowDialog(exception, "Error");
                }

            }
        });
        addSMStext.setText("");
    }

    /**
     * Refresh the list with the items in the Mobile Service Table
     */
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
        AzureTable.where().field("complete").eq(val(false)).execute(new TableQueryCallback<Type_SMS>() {

            public void onCompleted(List<Type_SMS> result, int count, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    adapter_sms.clear();

                    for (Type_SMS item : result) {
                        adapter_sms.add(item);
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
                    if (progressbar != null) progressbar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            nextServiceFilterCallback.onNext(request, new ServiceFilterResponseCallback() {
                @Override
                public void onResponse(ServiceFilterResponse response, Exception exception) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (progressbar != null) progressbar.setVisibility(ProgressBar.GONE);
                        }
                    });

                    if (responseCallback != null) responseCallback.onResponse(response, exception);
                }
            });
        }
    }
}

