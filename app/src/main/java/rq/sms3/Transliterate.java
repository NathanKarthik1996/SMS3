package rq.sms3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.telephony.EncodeException;

public class Transliterate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//DB_Controller db;
    GsmAlphabet nwet;
    String dataa;
    byte a[];
    String s;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db=new DB_Controller(this);
        setContentView(R.layout.activity_transliterate);
        Spinner dropdown = (Spinner) findViewById(R.id.spinner1);
        txt=(TextView) findViewById(R.id.data_id);
        txt.append("hello");

        String[] items = new String[]{"Bengali", "Gujurati", "Hindi","Kannada","Malayalam","Oriya","Punjabi","Tamil","Telugu","Urdu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        s=getIntent().getStringExtra("MESSAGE");
 //  s="சீமான்";
        GsmAlphabet.TextEncodingDetails data3;
        int tables[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13};
        nwet.setEnabledLockingShiftTables(tables);
        nwet.setEnabledSingleShiftTables(tables);
        data3=nwet.countGsmSeptets(s,true);
        try {
            a = nwet.stringToGsm7BitPacked(s, data3.languageTable, data3.languageTable);
        }catch (EncodeException e)
        {
            Toast.makeText(this, "Encode Exxception in transliteration", Toast.LENGTH_SHORT).show();
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
               // Toast.makeText()

  //              Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,4,4);
                txt.setText("");
                txt.append(dataa);

                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
    ///            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
       //         Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,5,5);
                txt.setText("");
                txt.append(dataa);

                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
            //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,6,6);
                txt.setText("");
                txt.append(dataa);
                break;
            case 3:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,7,7);
                txt.setText("");
                txt.append(dataa);
                break;
            case 4:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,8,8);
                txt.setText("");
                txt.append(dataa);
                break;
            case 5:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,9,9);
                txt.setText("");
                txt.append(dataa);
                break;
            case 6:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,10,10);
                txt.setText("");
                txt.append(dataa);
                break;
            case 7:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,11,11);
                txt.setText("");
                txt.append(dataa);
                break;
            case 8:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,12,12);
                txt.setText("");
                txt.append(dataa);
                break;
            case 9:
                // Whatever you want to happen when the thrid item gets selected
                //    Toast.makeText(this, "three", Toast.LENGTH_SHORT).show();
                dataa=nwet.gsm7BitPackedToString(a,1,a[0],0,13,13);
                txt.setText("");
                txt.append(dataa);
                break;
        }
    }
}