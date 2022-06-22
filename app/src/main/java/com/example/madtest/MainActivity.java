package com.example.madtest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madtest.DBhelper.DBHelper;
import com.example.madtest.DBhelper.Users;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editName, editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.user_name);
        editPassword = findViewById(R.id.password);
    }

    public void saveUser(View view){
        String user_name = editName.getText().toString();
        String password = editPassword.getText().toString();
        DBHelper bdhelper = new DBHelper(this);

        if(user_name.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }else {

            long result = bdhelper.addInfo(user_name,password);

            if(result>0){
                Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void readAllUserNames(View view){
        List<Users> name = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(this);
         name = dbHelper.getInfo();
        System.out.println(name.toString());
        Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show();
    }

    public void getSpecificData(View view){
       try{
           DBHelper dbHelper = new DBHelper(this);
           Users user = dbHelper.getStudent(editName.getText().toString());
           Toast.makeText(this, user.getUsername(), Toast.LENGTH_SHORT).show();
       }catch (Exception e){
           Toast.makeText(this, "No Such User", Toast.LENGTH_SHORT).show();
       }
    }

    public void deleteUser(View view){
        try{
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.deleteInfo(editName.getText().toString());
            Toast.makeText(this, "deleted users", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
}