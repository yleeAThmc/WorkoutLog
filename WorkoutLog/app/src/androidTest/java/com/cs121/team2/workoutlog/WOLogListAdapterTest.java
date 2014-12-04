package com.cs121.team2.workoutlog;

import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sam on 12/4/2014.
 */


//TODO: Sam E is responsible for this class

public class WOLogListAdapterTest extends AndroidTestCase{

    private WOLogListAdapter mAdapter;

    private WOLog log1;
    private WOLog log2;

    public WOLogListAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<WOLog> data = new ArrayList<WOLog>();

        log1 = new WOLog();
        log2 = new WOLog();
        data.add(log1);
        data.add(log2);
        mAdapter = new WOLogListAdapter(getContext(), data);
    }

//Sample adapter tests below from stackoverflow

//    public void testGetItem() {
//        assertEquals("John was expected.", mJohn.getName(),
//                ((Contact) mAdapter.getItem(0)).getName());
//    }
//
//    public void testGetItemId() {
//        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
//    }
//
//    public void testGetCount() {
//        assertEquals("Contacts amount incorrect.", 2, mAdapter.getCount());
//    }
//
//    // I have 3 views on my adapter, name, number and photo
//    public void testGetView() {
//        View view = mAdapter.getView(0, null, null);
//
//        TextView name = (TextView) view
//                .findViewById(R.id.text_contact_name);
//
//        TextView number = (TextView) view
//                .findViewById(R.id.text_contact_number);
//
//
//        //On this part you will have to test it with your own views/data
//        assertNotNull("View is null. ", view);
//        assertNotNull("Name TextView is null. ", name);
//        assertNotNull("Number TextView is null. ", number);
//        assertNotNull("Photo ImageView is null. ", photo);
//
//        assertEquals("Names doesn't match.", mJohn.getName(), name.getText());
//        assertEquals("Numbers doesn't match.", mJohn.getNumber(),
//                number.getText());
//    }
}
