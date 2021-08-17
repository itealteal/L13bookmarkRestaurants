package sg.edu.rp.id20033824.bookmarkrestaurants;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnToggle;
    Button btnInsert;
    ListView lv;
    CustomAdapter aa;
    ArrayList<restaurant> ar;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToggle = findViewById(R.id.btnToggle);
        btnInsert = findViewById(R.id.btnInsert);
        lv = findViewById(R.id.lv);
        spinner = findViewById(R.id.spinner);

        ar = new ArrayList<restaurant>();
        aa = new CustomAdapter(this, R.layout.row, ar);
        lv.setAdapter(aa);

        DBHelper dbh = new DBHelper(MainActivity.this);
        ar.clear();
        ar.addAll(dbh.getAllRestaurant());
        aa.notifyDataSetChanged();
        spinnerData();

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnToggle.getText().toString().equalsIgnoreCase("Show only 5 star Restaurant")){
                    btnToggle.setText("Show All Restaurant");
                    btnToggle.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    DBHelper dbh = new DBHelper(MainActivity.this);
//                    ArrayAdapter<Song> aatemp = new ArrayAdapter<Song>(SecondActivity.this, android.R.layout.simple_list_item_1,temp);
                    ar.clear();
                    ar.addAll(dbh.getAllRestaurantsByStar());
                    aa.notifyDataSetChanged();
                }else{
                    btnToggle.setText("Show only 5 star Restaurant");
                    btnToggle.setBackgroundColor(getResources().getColor(R.color.teal_200));
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    ArrayList<restaurant> temp = new ArrayList<restaurant>();
//                    ArrayAdapter<Song> aatemp = new ArrayAdapter<Song>(SecondActivity.this, android.R.layout.simple_list_item_1,temp);
                    ar.clear();
                    ar.addAll(dbh.getAllRestaurant());
                    aa.notifyDataSetChanged();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                restaurant data = ar.get(position);
                Intent i = new Intent(MainActivity.this,
                        SecondActivity.class);
                i.putExtra("data",data);
                startActivity(i);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.insert,null);

                final EditText etName = viewDialog.findViewById(R.id.etName);
                final EditText etCuisine = viewDialog.findViewById(R.id.etCuisine);
                final EditText etDescription = viewDialog.findViewById(R.id.etDescription);
                final RatingBar rbStars = viewDialog.findViewById(R.id.rbStars);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Add new Restaurant");
                myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(etName.getText().toString().isEmpty()||etCuisine.getText().toString().isEmpty()||etDescription.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this,"Please fill in all field",Toast.LENGTH_SHORT).show();
                        }else {
                            String name = etName.getText().toString();
                            String cuisine = etCuisine.getText().toString();
                            String description = etDescription.getText().toString();
                            int stars = (int) rbStars.getRating();
                            DBHelper dbh = new DBHelper(MainActivity.this);
                            long inserted_id = dbh.insertRestaurant(name, cuisine, description, stars);
                            if (inserted_id != -1) {
                                Toast.makeText(MainActivity.this, "Insert",
                                        Toast.LENGTH_SHORT).show();
                            }
                            ar.clear();
                            ar.addAll(dbh.getAllRestaurant());
                            aa.notifyDataSetChanged();
                        }

                    }
                });
                myBuilder.setNegativeButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    ar.clear();
                    ar.addAll(dbh.getAllRestaurant());
                    aa.notifyDataSetChanged();
                }else {
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    ar.clear();
                    ar.addAll(dbh.getAllRestaurantsByCuisine(spinner.getSelectedItem().toString()));
                    aa.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(MainActivity.this);
        ar.clear();
        ar.addAll(dbh.getAllRestaurant());
        aa.notifyDataSetChanged();
        spinnerData();
    }

    private void spinnerData(){
        ArrayList<String> cuisines = new ArrayList<String>();
        ArrayAdapter<String> ay = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,cuisines);
        spinner.setAdapter(ay);
        DBHelper dbh = new DBHelper(MainActivity.this);
        ArrayList<String> temp = dbh.getAllCuisine();
        cuisines.clear();
        cuisines.add("No filter");
        for(int i =0;i < temp.size();i++){
            if(!cuisines.contains(temp.get(i))){
                cuisines.add(temp.get(i));
            }
        }
        ay.notifyDataSetChanged();
    }
}