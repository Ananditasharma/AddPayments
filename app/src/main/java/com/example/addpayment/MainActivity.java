package com.example.addpayment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSave;
    RecyclerView recyclerView;
    private Context mContext;
    EditText et_amount,et_ProviderName,et_Transaction;
    TextView Cancel,tv_addPayment;
    Button btnOk;
    String amountEntered,transaction,provider;
    RelativeLayout mRelativeLayout;
    String paymentMode;
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static FileWriter file;
    ArrayList<String> amountList = new ArrayList<>();
    ArrayList<String> paymentsType = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListeners();
        load();

    }


    // This method is to load data from LastPayment.txt file as our Main Activity appears.
    public void load()
    {
        try {
            FileInputStream inputStream = openFileInput("LastPayment.txt");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            JSONObject obj = new JSONObject();
            obj.getString("PaymentsMode");
            obj.getString("Amounts");
            in.readObject();
            in.close();
            inputStream.close();

        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // This method is to write data to LastPayment.txt file in json format.

    private void fileWrite() {
        File file= null;
        try {
            file = getFilesDir();
            FileOutputStream fileOutputStream = openFileOutput("LastPayment.txt", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            JSONObject obj = new JSONObject();
            obj.put("Amounts", amountList);
            obj.put("PaymentsMode", paymentsType);
            out.writeObject(obj.toString());
            Toast.makeText(this, "Saved \n" + "Path --" + file + "\tLastPayment.txt", Toast.LENGTH_SHORT).show();
            out.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // This method is to define Views of xml file.
    private void findViews(){
        btnSave = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.recycler_view);
        tv_addPayment = findViewById(R.id.tv_addPayment);
    }

    // This method is to define OnClickListeners
    private void setClickListeners(){
        btnSave.setOnClickListener(this);
        tv_addPayment.setOnClickListener(this);
    }

    // This method is to set Adapter for TagView
    private void AmountTags(){

        amountList.add( amountEntered);
        paymentsType.add(paymentMode);
        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new TagAdapter(mContext,amountList,paymentsType);
        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }

    //This method is for actions on OnClickListeners
    @Override
    public void onClick(View view) {
        if(view==tv_addPayment){
            AddPaymentDialog();
        }
        if(view==btnSave){
            fileWrite();
        }
    }

    //This method is called when we open a dialog box on click on Add Payments option.
    private void AddPaymentDialog()
    {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.activity_addpayment);
        myDialog.setCancelable(false);
        // Define spinner for Payment Options.
        final Spinner spinner = (Spinner) myDialog.findViewById(R.id.spinner);
        // Defined all views inside dialog that appears.
        et_amount = (EditText) myDialog.findViewById(R.id.et_amount);
        et_Transaction = (EditText) myDialog.findViewById(R.id.et_Transaction);
        et_ProviderName = (EditText) myDialog.findViewById(R.id.et_providerName);
        Cancel =  myDialog.findViewById(R.id.Cancel);
        btnOk = myDialog.findViewById(R.id.btnOk);

        // Initializing a String Array
        String[] paymentMethod = new String[]{
                "Cash",
                "BankTransfer",
                "CreditCard"
        };

        // Initializing an ArrayAdapter for apinner elements.
        final ArrayAdapter <String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,paymentMethod
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        //This method is called for selected item .
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                paymentMode=spinner.getSelectedItem().toString();

                // If Bank Transfer is selected
                if(paymentMode=="BankTransfer"){
                    et_ProviderName.setVisibility(View.VISIBLE);
                    et_Transaction.setVisibility(View.VISIBLE);
                }
                // If payment mode is selected as Cash.
                else if (paymentMode=="Cash"){
                    et_ProviderName.setVisibility(View.GONE);
                    et_Transaction.setVisibility(View.GONE);
                }
                // If payment mode is selected as Credit card.
                else{
                    et_ProviderName.setVisibility(View.GONE);
                    et_Transaction.setVisibility(View.GONE);
                }
            }
            // If noting is selected in spinner
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                Toast toast= Toast. makeText(getApplicationContext(),"Please Select one Payment mode",Toast. LENGTH_SHORT);
                toast.show();

            }
        });
        // On action of Okay button in dialog box.
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountEntered = String.valueOf(et_amount.getText());
                transaction = String.valueOf(et_Transaction.getText());
                provider = String.valueOf(et_ProviderName.getText());
                AmountTags();
                myDialog.dismiss();
            }
        });

        //Dismiss the Dialog on cancel button.
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();

    }
}
