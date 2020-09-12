package id.radikz.githubapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search_image);

        search.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, CoderActivity.class);
                startActivity(i);
            }});

    }

}
