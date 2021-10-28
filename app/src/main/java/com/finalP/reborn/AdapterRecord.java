package com.finalP.reborn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord>{
    private Context context;
    private ArrayList<ModelRecord> recordslist;


    MyDbHelper dbHelper;

    public AdapterRecord(Context context, ArrayList<ModelRecord> recordslist) {
        this.context = context;
        this.recordslist = recordslist;

        dbHelper=new MyDbHelper(context);
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_record,parent,false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, final int position) {
        ModelRecord model=recordslist.get(position);
        final String id=model.getId();
        final String image=model.getImage();
        final String name=model.getName();
        final String mobile=model.getMobile();
        final String address=model.getAddress();
        final String sqft=model.getSqft();
        final String balcony=model.getBalcony();
        final String bhk=model.getBhk();
        final String price=model.getPrice();
        final String date=model.getDate();
        final String des=model.getDes();
        final String addedTime=model.getAddedTime();
        final String updatedTime=model.getUpdatedTime();

        holder.name.setText(name);
        holder.balcony.setText(balcony);
        holder.mobile.setText(mobile);
        holder.address.setText(address);
        holder.age.setText(sqft);

        if(image.equals("null")){
            holder.homei.setImageResource(R.drawable.ic_person);

        }
        else {
            holder.homei.setImageURI(Uri.parse(image));

        }
//        holder.name.setText(name);
//        holder.name.setText(name);
//        holder.name.setText(name);
//        holder.name.setText(name);
//        holder.name.setText(name);
//        holder.name.setText(name);
//        holder.name.setText(name);
//        holder.name.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,RecordDetailActivity.class);
                intent.putExtra("RECORD_ID",id);
                context.startActivity(intent);


            }
        });
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreDialog(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+mobile,
                        ""+address,
                        ""+sqft,
                        ""+balcony,
                        ""+bhk,
                        ""+price,
                        ""+date,
                        ""+des,
                        ""+image,
                        ""+updatedTime,
                        ""+addedTime
                        );

            }
        });



    }

    private void showMoreDialog(String position, final String id, final String name, final String mobile, final String address, final String sqft, final String balcony, final String bhk, final String price, final String date, final String des, final String image, final String updatedTime, final String addedTime) {
        String[] options={"Edit","Delete"};
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent intent=new Intent(context,AddRecord.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("NAME",name);
                    intent.putExtra("MOBILE",mobile);
                    intent.putExtra("ADDRESS",address);
                    intent.putExtra("SQFT",sqft);
                    intent.putExtra("BALCONY",balcony);
                    intent.putExtra("BHK",bhk);
                    intent.putExtra("PRICE",price);
                    intent.putExtra("DATE",date);
                    intent.putExtra("DES",des);
                    intent.putExtra("IMAGE",image);
                    intent.putExtra("UPDATEDTIME",updatedTime);
                    intent.putExtra("ADDEDTIME",addedTime);
                    intent.putExtra("isEditMode",true);
                    context.startActivity(intent);



                }
                else if(which==1){


                    dbHelper.deleteData(id);
                    ((MainActivity)context).onResume();



                }

            }
        });
        builder.create().show();

    }

    @Override
    public int getItemCount() {
        return recordslist.size();
    }

    class  HolderRecord extends RecyclerView.ViewHolder{
        ImageView homei;
        TextView name,bhk,price,address,balcony,age,mobile;
        ImageButton moreBtn;

        public HolderRecord(@NonNull View itemView) {
            super(itemView);
            homei=itemView.findViewById(R.id.homei);
            name=itemView.findViewById(R.id.name);
            bhk=itemView.findViewById(R.id.bhk);
            balcony=itemView.findViewById(R.id.balcony);
            price=itemView.findViewById(R.id.price);
            address=itemView.findViewById(R.id.address);
            age=itemView.findViewById(R.id.sqft);
            mobile=itemView.findViewById(R.id.mobile);
            moreBtn=itemView.findViewById(R.id.moreBtn);

        }
    }
}
