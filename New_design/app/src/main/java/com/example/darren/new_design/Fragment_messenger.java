package com.example.darren.new_design;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.*;

import java.net.MalformedURLException;
import java.util.List;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class Fragment_messenger extends Fragment implements ListView.OnItemClickListener{

    ArrayList<Type_Contact> fetch = new ArrayList<>();
    Adapter_Contact adapter;
    ListView lv;
    TextView Person;

    MobileServiceClient mClient;
    MobileServiceTable<ToDoItem> mToDoTable;
    Adapter_SMS mAdapter;
    EditText mTextNewToDo;
    ProgressBar mProgressBar;
    Button buttonAddToDo;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View InputFragmentView = inflater.inflate(R.layout.messenger, container, false);
        String[] values = new String[] {
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


   /*     listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });*/
        for(int i = 0; i < values.length; i++) {
            fetch.add(new Type_Contact(values[i],i + " New message"));
        }

        lv =(ListView)InputFragmentView.findViewById(R.id.listview);
        adapter = new Adapter_Contact(getActivity(), fetch);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        Person = (TextView) InputFragmentView.findViewById(R.id.Person);

        mProgressBar = (ProgressBar)  InputFragmentView.findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);

        try {
            // Create the Mobile Service Client instance, using the provided
            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://finalyp.azure-mobile.net/",
                    "ohpiRZauiBLIDIrtQCmGRfTfdwGfow54",
                    getActivity()).withFilter(new ProgressFilter());

            // Get the Mobile Service Table instance to use
            mToDoTable = mClient.getTable(ToDoItem.class);

            mTextNewToDo = (EditText)  InputFragmentView.findViewById(R.id.textNewToDo);
            buttonAddToDo = (Button)  InputFragmentView.findViewById(R.id.buttonAddToDo);
            buttonAddToDo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem();
                }
            });

            // Create an adapter to bind the items with the view
            mAdapter = new Adapter_SMS(getActivity(), R.layout.row_sms);
            ListView listViewToDo = (ListView)  InputFragmentView.findViewById(R.id.listViewToDo);
            listViewToDo.setAdapter(mAdapter);

            // Load the items from the Mobile Service
            refreshItemsFromTable();

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        }

        return InputFragmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String person = fetch.get(position).getName();
        Person.setText(person);
        Toast.makeText(getActivity(), person, Toast.LENGTH_SHORT).show();
    }


    /**
     * Initializes the activity menu
     */
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.activity_main, menu);
    //    return true;
    //}

    /**
     * Select an option from the menu
     */
    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    //     if (item.getItemId() == R.id.menu_refresh) {
    //         refreshItemsFromTable();
    //    }

    //    return true;
    //}

    public void checkItem(ToDoItem item) {
        if (mClient == null) {
            return;
        }

        // Set the item as completed and update it in the table
        item.setComplete(true);

        mToDoTable.update(item, new TableOperationCallback<ToDoItem>() {

            public void onCompleted(ToDoItem entity, Exception exception, ServiceFilterResponse response) {
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

    public void addItem() {
        if (mClient == null) {
            return;
        }

        // Create a new item
        ToDoItem item = new ToDoItem();

        item.setText(mTextNewToDo.getText().toString());
        item.setComplete(false);

        // Insert the new item
        mToDoTable.insert(item, new TableOperationCallback<ToDoItem>() {

            public void onCompleted(ToDoItem entity, Exception exception, ServiceFilterResponse response) {

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
        mToDoTable.where().field("complete").eq(val(false)).execute(new TableQueryCallback<ToDoItem>() {

            public void onCompleted(List<ToDoItem> result, int count, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    mAdapter.clear();

                    for (ToDoItem item : result) {
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
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
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

                    if (responseCallback != null)  responseCallback.onResponse(response, exception);
                }
            });
        }
    }
}
