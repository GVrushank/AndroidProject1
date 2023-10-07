package diamond.guidediamond.fffdiamondsapps.mixadproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ads.lodInt(this);

        min = findViewById(R.id.min);

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Ads.showIntCall(MainActivity.this,true);

                new Ads(new OnDismiss() {
                    @Override
                    public void onDismiss() {

                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));

                    }
                }).showIntCall(MainActivity.this,true);
            }
        });

    }
}