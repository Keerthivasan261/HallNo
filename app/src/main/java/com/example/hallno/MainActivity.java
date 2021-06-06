package com.example.hallno;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity {


    private TextView result;
    private EditText start_reg;
    private EditText end_reg;
    private EditText hall_no;
    private EditText regis_num;
    private EditText pass;
    private Button hall_no_but;
    private Button admin_but;
    private Button update;
    private Button home;

    private Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        result = (TextView) findViewById(R.id.result);
        start_reg = (EditText) findViewById(R.id.start_reg);
        end_reg = (EditText) findViewById(R.id.end_reg);
        hall_no = (EditText) findViewById(R.id.hall_no);
        regis_num = (EditText) findViewById(R.id.regis_num);
        pass = (EditText) findViewById(R.id.pass);
        hall_no_but = (Button) findViewById(R.id.hall_no_but);
        admin_but = (Button) findViewById(R.id.admin_but);
        update = (Button) findViewById(R.id.update);
        home = (Button) findViewById(R.id.home);

        hall_no_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                regis_num.setVisibility(View.GONE);
                regis_num.setEnabled(false);
                pass.setVisibility(View.GONE);
                pass.setEnabled(false);
                hall_no_but.setVisibility(View.GONE);
                hall_no_but.setEnabled(false);
                admin_but.setVisibility(View.GONE);
                admin_but.setEnabled(false);
                start_reg.setVisibility(View.GONE);
                start_reg.setEnabled(false);
                end_reg.setVisibility(View.GONE);
                end_reg.setEnabled(false);
                hall_no.setVisibility(View.GONE);
                hall_no.setEnabled(false);
                update.setVisibility(View.GONE);
                update.setEnabled(false);
                result.setVisibility(View.VISIBLE);
                result.setEnabled(true);
                home.setVisibility(View.VISIBLE);
                home.setEnabled(true);
                checkReg(regis_num.getText().toString());
            }
        });

        admin_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                FirebaseAuth _auth =FirebaseAuth.getInstance();
                _auth.getInstance().signInWithEmailAndPassword(regis_num.getText().toString(),pass.getText().toString());
                if(_auth.getCurrentUser()!=null){
                    regis_num.setVisibility(View.GONE);
                    regis_num.setEnabled(false);
                    pass.setVisibility(View.GONE);
                    pass.setEnabled(false);
                    hall_no_but.setVisibility(View.GONE);
                    hall_no_but.setEnabled(false);
                    admin_but.setVisibility(View.GONE);
                    admin_but.setEnabled(false);
                    start_reg.setVisibility(View.VISIBLE);
                    start_reg.setEnabled(true);
                    end_reg.setVisibility(View.VISIBLE);
                    end_reg.setEnabled(true);
                    hall_no.setVisibility(View.VISIBLE);
                    hall_no.setEnabled(true);
                    update.setVisibility(View.VISIBLE);
                    update.setEnabled(true);
                    result.setVisibility(View.GONE);
                    result.setEnabled(false);
                    home.setVisibility(View.VISIBLE);
                    home.setEnabled(true);
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                UploadReg(start_reg.getText().toString(),end_reg.getText().toString(),hall_no.getText().toString());
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                FirebaseAuth _auth = FirebaseAuth.getInstance();
                _auth.signOut();
                regis_num.setVisibility(View.VISIBLE);
                regis_num.setEnabled(true);
                pass.setVisibility(View.VISIBLE);
                pass.setEnabled(true);
                hall_no_but.setVisibility(View.VISIBLE);
                hall_no_but.setEnabled(true);
                admin_but.setVisibility(View.VISIBLE);
                admin_but.setEnabled(true);
                start_reg.setVisibility(View.GONE);
                start_reg.setEnabled(false);
                end_reg.setVisibility(View.GONE);
                end_reg.setEnabled(false);
                hall_no.setVisibility(View.GONE);
                hall_no.setEnabled(false);
                update.setVisibility(View.GONE);
                update.setEnabled(false);
                result.setVisibility(View.GONE);
                result.setEnabled(false);
                home.setVisibility(View.GONE);
                home.setEnabled(false);
            }
        });
    }
    private void initializeLogic() {
        regis_num.setVisibility(View.VISIBLE);
        regis_num.setEnabled(true);
        pass.setVisibility(View.VISIBLE);
        pass.setEnabled(true);
        hall_no_but.setVisibility(View.VISIBLE);
        hall_no_but.setEnabled(true);
        admin_but.setVisibility(View.VISIBLE);
        admin_but.setEnabled(true);
        start_reg.setVisibility(View.GONE);
        start_reg.setEnabled(false);
        end_reg.setVisibility(View.GONE);
        end_reg.setEnabled(false);
        hall_no.setVisibility(View.GONE);
        hall_no.setEnabled(false);
        update.setVisibility(View.GONE);
        update.setEnabled(false);
        result.setVisibility(View.GONE);
        result.setEnabled(false);
        home.setVisibility(View.GONE);
        home.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels(){
        return getResources().getDisplayMetrics().heightPixels;
    }

    public void checkReg(String Reg) {
        final String[] hallNo = {""};
        try {
            DatabaseReference referance = FirebaseDatabase.getInstance().getReference().child("RegisterNo").child(Reg);
            referance.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    result.setText(snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    public void UploadReg(String startReg,String endReg,String HallNo){
        int start = Integer.parseInt(startReg.substring(startReg.length()-4,startReg.length()));
        int end = Integer.parseInt(endReg.substring(endReg.length()-4,endReg.length()));
        for(int i=start;i<=end;i++){
            String temp = "61121810"+ String.valueOf(i);
            updateHall(temp,HallNo);
        }
    }
    public void updateHall(String reg,String hallNo) {
        try {
            FirebaseDatabase.getInstance().getReference().child("RegisterNo").child(reg).child("HallNo").setValue(hallNo);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}




/*
package com.example.hallno;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public Button button;
    public int result;
    public Button button2;
    public EditText regno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regno = (EditText)findViewById(R.id.regis_num);
        button = (Button)findViewById(R.id.hall_no_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = checkReg(regno.getText().toString());
                //Intent intent = new Intent(MainActivity.this,hallno.class);
                //startActivity(intent);
            }
        });
        button2 = (Button)findViewById(R.id.admin_but);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,update.class);
               //startActivity(intent);
            }
        });

        }


    public int checkReg(String Reg) {
        final int[] HallNo = new int[1];
        DatabaseReference referance = FirebaseDatabase.getInstance().getReference().child("RegisterNo").child(Reg);
        referance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    HallNo[0] = (int) snapshot.getValue();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        System.out.println(HallNo[0]);
        return HallNo[0];

}
}*/
