package com.marwadibrothers.mbstatus.activity.editing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.PackageActivity;
import com.marwadibrothers.mbstatus.utils.Config;

public class PlanPopupActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_popup);
        context=PlanPopupActivity.this;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Your plan is about to expired please renew it now.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Renew Now",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Config.ifFromPopup=true;
                        Intent i=new Intent(context, PackageActivity.class);
                        startActivity(i);
                        finish();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}