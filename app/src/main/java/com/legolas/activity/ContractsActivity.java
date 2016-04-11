package com.legolas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.legolas.VO.SignContract;

/**
 * Created by legolas on 2016/3/28.
 */
public class ContractsActivity extends AppCompatActivity {
    private TextView backTV;
    private ImageView signContractsIV;
    private ImageView contractsIV;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.contracts_view);
        backTV = (TextView)findViewById(R.id.backTV);
        signContractsIV = (ImageView)findViewById(R.id.signContractsIV);
        contractsIV = (ImageView)findViewById(R.id.contractsIV);
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContractsActivity.this.finish();
            }
        });

        signContractsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractsActivity.this , SignContractsWorkerActivity.class);
                startActivity(intent);
            }
        });

        contractsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContractsActivity.this , ContractsWorkerActivity.class);
                startActivity(intent);
            }
        });
    }
}
