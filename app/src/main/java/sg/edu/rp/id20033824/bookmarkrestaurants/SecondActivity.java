package sg.edu.rp.id20033824.bookmarkrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText etId,etTitle2,etSingers2,etYear2;
    RatingBar rbStars2;
    Button btnUpdate,btnDelete,btnCancel;
    restaurant data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        etId = findViewById(R.id.etId);
        etTitle2 = findViewById(R.id.etTitle);
        etSingers2 = findViewById(R.id.etSingers);
        etYear2 = findViewById(R.id.etYear);
        rbStars2 = findViewById(R.id.rbStars2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (restaurant) i.getSerializableExtra("data");

        etId.setText(String.valueOf(data.getId()));
        etTitle2.setText(data.getName());
        etSingers2.setText(data.getDescription());
        etYear2.setText(data.getCuisine());
        rbStars2.setRating((data.getStars()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTitle2.getText().toString().isEmpty()||etSingers2.getText().toString().isEmpty()||etYear2.getText().toString().isEmpty()) {
                    Toast.makeText(SecondActivity.this,"Please fill in all field",Toast.LENGTH_SHORT).show();
                }else {
                    DBHelper dbh = new DBHelper(SecondActivity.this);
                    data.setName(etTitle2.getText().toString());
                    data.setDescription(etSingers2.getText().toString());
                    data.setCuisine(etYear2.getText().toString());
                    data.setStars((int) rbStars2.getRating());
                    dbh.updateRestaurant(data);
                    dbh.close();
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);

                myBuilder.setTitle("Warning");
                myBuilder.setMessage("Are you sure you want to delete this restaurant?");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Confirm delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        dbh.deleteRestaurant(data.getId());
                        finish();
                    }
                });

                myBuilder.setNeutralButton("Cancel",null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);

                myBuilder.setTitle("Warning");
                myBuilder.setMessage("Are you sure you want to discard your changes?");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Confirm discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                myBuilder.setNeutralButton("Cancel",null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });
    }
}