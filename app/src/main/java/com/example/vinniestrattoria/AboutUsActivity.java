/** ONI LUCA 20200008@student.act.edu
   CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
   The AboutUsActivity class represents the about section of the Vinnie's Trattoria app.
   Contains iformation about the restaurant from founders to locations etc.*/

package com.example.vinniestrattoria;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_activity_layout);
    }
}