package rq.sms3;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.telephony.EncodeException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // GsmAlphabet nwet;
    ArrayList<String> smsMessagesList = new ArrayList<>();
    ListView messages;
    byte[] a;
    String tosend="";
    String temp="",temp2="";
    DB_Controller db;
    int flag=0,x=0;
    TextView textView;
    short port=6789;
    //    short port =15820;
    GsmAlphabet.TextEncodingDetails data3;
    ArrayAdapter arrayAdapter;
    int tables[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13};
    EditText input,tvNumber;
    SmsManager smsManager = SmsManager.getDefault();
    private static MainActivity inst;

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages = (ListView) findViewById(R.id.messages);
        input = (EditText) findViewById(R.id.input);
        tvNumber=(EditText) findViewById(R.id.phoneno);
        textView=(TextView) findViewById(R.id.textView);
//        db.insert_album("");
        db=new DB_Controller(this);
        input.addTextChangedListener(mTextEditorWatcher);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        GsmAlphabet.setEnabledLockingShiftTables(tables);
        //   nwet.setEnabledSingleShiftTables(tables);

        messages.setAdapter(arrayAdapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            // refreshSmsInbox();
        }


    }
    public TextWatcher mTextEditorWatcher=new TextWatcher()
    {
        @Override
        public void afterTextChanged(Editable s) {
            textView.setText(String.valueOf(s.length()));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           textView.setText(String.valueOf(s.length()));
        }
    };

    public void updateInbox(final String smsMessage ) {
        //flag=x;
        x=0;
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
        try{
            db.insert_album(smsMessage);
        }catch(SQLiteException e){
            Toast.makeText(MainActivity.this,"ALREADY EXISTS",Toast.LENGTH_SHORT).show();
        }
    }
   public void Translit(View view){
       Intent t=new Intent(this,Transliterate.class);
       t.putExtra("MESSAGE",temp);
       startActivity(t);
       temp="";
       tosend="";
    }
    public void onSendClick(View view) {
        if(tvNumber.getText().toString()==null)
        {
            Toast.makeText(MainActivity.this,"Please enter your phone number",Toast.LENGTH_SHORT).show();
        }
        else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                getPermissionToReadSMS();
            } else {
                try {

                    data3 = GsmAlphabet.countGsmSeptets(input.getText().toString(), true);
                    String IN = input.getText().toString();
                    if (IN.length() > 149) {
                        String s = "",s1="";
                        for (int l = 0; l < 149; l++) {
                            s += IN.charAt(l);

                        }
                        byte[] a = GsmAlphabet.stringToGsm7BitPacked(s, data3.languageTable, data3.languageTable);
                        byte[] a2 = new byte[a.length + 1];
                        a2[0] = a[0];
                        a2[1] = (byte) data3.languageTable;
                        int k = 2;
                        for (int j = 1; j < a.length; j++) {
                            a2[k] = a[j];
                            k++;
                        }
                        //String decoded = new String(a2, "ISO-8859-1");
                        System.out.println(data3.languageTable);
                        if (tvNumber.getText().toString() == "") {
                            Toast.makeText(MainActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                        } else {

                            smsManager.sendDataMessage(tvNumber.getText().toString(), null, port, a2, null, null);
                           // input.setText("");
                            //tvNumber.setText("");
                            Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                        }

                        for (int l = 149; l < IN.length(); l++) {
                            s1 += IN.charAt(l);

                        }
                        byte[] a3 = GsmAlphabet.stringToGsm7BitPacked(s1, data3.languageTable, data3.languageTable);
                        byte[] a4 = new byte[a.length + 1];
                        a4[0] = a3[0];
                        a4[1] = (byte) data3.languageTable;
                        int l = 2;
                        for (int j = 1; j < a3.length; j++) {
                            a4[l] = a3[j];
                            l++;
                        }
                        //String decoded = new String(a2, "ISO-8859-1");
                        System.out.println(data3.languageTable);
                        if (tvNumber.getText().toString() == "") {
                            Toast.makeText(MainActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                        } else {

                            smsManager.sendDataMessage(tvNumber.getText().toString(), null, port, a4, null, null);
                            //input.setText("");
                            //tvNumber.setText("");
                            Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                        }


                    } else {


                        a = GsmAlphabet.stringToGsm7BitPacked(input.getText().toString(), data3.languageTable, data3.languageTable);
                        //\\ a = GsmAlphabet.stringToGsm7BitPacked("முன்னாள் அமைச்சரும், பாப்பிரெட்டிபட்டி எம்எல்ஏவுமான பழனியப்பன் தலைமையில், மாஜி அமைச்சர் செந்தில் பாலாஜி, தோப்பு வெங்கடசலம் போன்ற எம் தனி அணியாக, எடப்", 11, 11);
                        //            String s=new String(a);

                        byte[] a2 = new byte[a.length + 1];
                        a2[0] = a[0];
                        a2[1] = (byte) data3.languageTable;
                        int k = 2;
                        for (int j = 1; j < a.length; j++) {
                            a2[k] = a[j];
                            k++;
                        }
                        //String decoded = new String(a2, "ISO-8859-1");
                        int count2;
                        System.out.println(data3.languageTable);
                        if (tvNumber.getText().toString() == "") {
                            Toast.makeText(MainActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                        } else {

                            smsManager.sendDataMessage(tvNumber.getText().toString(), null, port, a2, null, null);
                            input.setText("");
                            tvNumber.setText("");
                            Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                        }// smsManager.sendTextMessage(tvNumber.getText().toString(), null, s, null, null);
                    }
                }
                catch (EncodeException e) {
                    Toast.makeText(MainActivity.this, "Encode Exception", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
            }
        }
        }

    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    READ_SMS_PERMISSIONS_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                // refreshSmsInbox();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }



    }

    public void refreshSmsInbox(View view) {
        //ContentResolver contentResolver = getContentResolver();

        //     if(flag==1){
        Cursor smsInboxCursor = db.list_album();

        // int indexBody = smsInboxCursor.getColumnIndex("MSG");
        // int indexAddress = smsInboxCursor.getColumnIndex("address");
        //if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        arrayAdapter.clear();
        x++;
        while (smsInboxCursor.moveToNext()) {
            String str =  smsInboxCursor.getString(0) +
                    "\n";// + smsInboxCursor.getString(indexBody) + "\n";
            // String str="";
            arrayAdapter.add(str);
            // flag = 0;
            // }

            if(smsInboxCursor.isLast()&&temp.length()<1)
            {
          //      if(tosend.charAt(24)==tosend.charAt(30))
           //     {
            //        Toast.makeText(this, "YESSS", Toast.LENGTH_SHORT).show();
            //    }
               // int count;
                //count=(int)tosend.charAt(30);
                if(x>1)
                {
                    temp=temp2;
                }
                else {
                    tosend += smsInboxCursor.getString(0);
                    for (int i = 0; i < tosend.length(); i++) {
                        //Toast.makeText(this, ""+tosend.charAt(i), Toast.LENGTH_SHORT).show();
                        if (tosend.charAt(i) == tosend.charAt(24)) {
                            flag++;
                        }
                        if (flag > 0) {
                            if (flag == 1) {
                                temp += tosend.charAt(i);
                                // Toast.makeText(this, ""+flag, Toast.LENGTH_SHORT).show();
                            }
                            if (tosend.charAt(i + 1) == 'D') {
                                break;
                            }

                        }
                        //temp+=tosend.charAt(i);
                    }
                }
             //   Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
           //     Toast.makeText(this, ""+(char)count, Toast.LENGTH_SHORT).show();
            //    Toast.makeText(this, ""+tosend.charAt(30), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
            temp2=temp;
            }
        }
//messages.setSelection(arrayAdapter.getCount() - 1);
    }



}
