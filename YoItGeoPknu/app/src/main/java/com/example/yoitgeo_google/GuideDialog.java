package com.example.yoitgeo_google;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class GuideDialog extends Dialog {
    GuideDialog guideDialog;
    public GuideDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.guide_dialog);

        guideDialog = this;

        Button bStart = findViewById(R.id.bStartApp);
        bStart.bringToFront();
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBtn(view);
            }
        });
    }

    public void onClickBtn(View view) {
        this.dismiss();
    }
}
