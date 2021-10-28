package com.finalP.reborn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;

//import android.widget.SearchView;


public class MainActivity extends AppCompatActivity {
//    private FloatingActionButton addRecordBtn;
    private RecyclerView recordsRv;
    private MyDbHelper dbHelper;
    ActionBar actionBar;
    String orderByNewest=Constants.C_ADDED_TIMESTAMP + " DESC ";
    String orderByOldest=Constants.C_ADDED_TIMESTAMP + " ASC ";
    String orderBypriceAsc=Constants.C_PRICE + " ASC ";
    String orderByprice=Constants.C_PRICE + " DESC ";

    String currentOrderByStatus = orderByNewest;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        addRecordBtn=findViewById(R.id.addRecordBtn);
        recordsRv=findViewById(R.id.recordsRv);
        dbHelper = new MyDbHelper(this);





        loadRecords(orderByNewest);

//        addRecordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                startActivity(new Intent(MainActivity.this,AddRecord.class));
////                actionBar =getSupportActionBar();
////                getSupportActionBar().setTitle("All Records");
//                Intent intent=new Intent(MainActivity.this,AddRecord.class);
//                intent.putExtra("isEditMode",false);
//                startActivity(intent);
//
//
//
//
//            }
//        });
    }

    private void loadRecords(String orderBy) {
        currentOrderByStatus=orderBy;
        AdapterRecord adapterRecord= new AdapterRecord(MainActivity.this,dbHelper.getAllRecord(orderBy));
        recordsRv.setAdapter(adapterRecord);

        getSupportActionBar().setSubtitle("Total :"+dbHelper.getRecordsCount());
    }
    private void searchRecords(String query){
        AdapterRecord adapterRecord= new AdapterRecord(MainActivity.this,dbHelper.searchRecord(query));
        recordsRv.setAdapter(adapterRecord);

        getSupportActionBar().setSubtitle("Total :"+dbHelper.getRecordsCount());

    }
    protected void onResume(){
        super.onResume();
        loadRecords(currentOrderByStatus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_sort){
            sortOptionDialog();

        }
        else if(id==R.id.action_delete_all){
            dbHelper.deleteAllData();
            onResume();


        }

        return super.onOptionsItemSelected(item);
    }

    private void sortOptionDialog() {
        String[] options={"Price Ascending","Price Descending","Newest","Oldest"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sort By").setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    loadRecords(orderBypriceAsc);

                }
                else if (which==1){
                    loadRecords(orderByprice);

                }
                else if (which==2){
                    loadRecords(orderByNewest);

                }
                else if (which==3){
                    loadRecords(orderByOldest);

                }

            }
        }).create().show();


    }
}
