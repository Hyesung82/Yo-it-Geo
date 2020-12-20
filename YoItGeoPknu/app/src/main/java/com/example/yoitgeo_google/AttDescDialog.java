package com.example.yoitgeo_google;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AttDescDialog extends Dialog {

    ImageView ivAtt;
    TextView tvTitle, tvDesc;

    private int id;

    String attTitles[] = {"모과나무", "백경동산", "히말라야시다 숲", "벚꽃길", "향파 이주홍 문학비", "도란뜰",
            "행복기숙사", "1호관"};
    String attDescs[] = {"정문 근처에 있는 모과나무로 수령은 200여년으로 추정된다. 의령 충익사 모과나무(300년), 마산 의림사 모과나무(250년)와 함께 대표적인 노거수(老巨樹) 모과나무로 꼽힌다.",
            "백경동산은 부경대 전신의 하나인 부산수산대 개교(1941년) 당시부터 지금까지 그 모습을 유지하고 있는 역사적인 공간이다. 소나무가 울창해 사색과 휴식의 공간으로 인기있는 곳이다. 백경동산 옆에는 백경탑이 자리하고 있다. 일찍이 한국원양어업을 개척하기 위해 사모아나 북태평양에 출어하였다가 불의의 사고로 꽃다운 청춘을 바친 부경대학교 동문들의 영혼을 기리기 위해 건립된 것이다. 동문들의 희생으로 우리나라 원양어업이 처음 개척되어 수산입국을 이룰 수 있었다.",
    "히말라야시다 숲은 학술정보관 옆에 있다. 70여 그루의 히말라야시다가 울창한 숲을 이루고 있고 그 숲속에 통나무 벤치들이 놓여있다. 도서관에서 공부하다 지친 학생들이 머리를 식히는 곳으로 애용되고 있다.",
            "봄이면 부경대는 벚꽃으로 덮인다. 특히 도란뜰 근처 벚꽃터널이 유명해 부경대의 상징적인 공간으로 자리 잡았다.",
    "향파 이주홍은 1949년부터 부경대학교(前 부산수산대학교) 교수로 재직하면서 후학을 양성하는 한편 소설뿐만 아니라 시 수필 희곡 동시 동화 번역 등 문학의 다양한 장르에 걸쳐 왕성한 활동으로 한국 문학사에 큰 업적을 남겼다.(1987년 1월 3일 별세). 대한민국 문화훈장, 대한민국 예술원상, 부산시문화상 등을 수상했다.\n" +
            "이 문학비는 이주홍문학재단이 2006년 6월 2일 향파 탄생 100주년 기념사업으로 건립했다. 향파의 사색과 사유의 공간이었던 종합강의동 앞에 있다.",
    "도란뜰은 대학본부와 가온관 사이에 있는 소나무 숲을 말한다. 300여 그루의 소나무 아래에 나무벤치들이 놓여있다. 학생뿐만 아니라 주민들의 쉼터로 인기 있는 공간이다.",
    "개발자가 살고 있는 곳이다.", "김영봉 교수님의 연구실이 있는 건물이다."};
    Integer attImages[] = {R.drawable.quince_tree, R.drawable.baekgyeong_hill,
            R.drawable.hymalayacedar_forest, R.drawable.cherry_blossom_road,
            R.drawable.hyangpa_literary_memorial_monument, R.drawable.doran_yard,
            R.drawable.happy_dorm, };

    AttDescDialog attDescDialog;
    public AttDescDialog(@NonNull Context context, int id) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.id = id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.att_desc_dialog);

        ivAtt = (ImageView) findViewById(R.id.ivAtt);
        tvTitle = (TextView) findViewById(R.id.tvAttTitle);
        tvDesc = (TextView) findViewById(R.id.tvAttDesc);

        ivAtt.setImageResource(attImages[id]);
        tvTitle.setText(attTitles[id]);
        tvDesc.setText(attDescs[id]);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.5f;
        getWindow().setAttributes(lpWindow);


        attDescDialog = this;

        ImageView ivClose = findViewById(R.id.ivAttClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
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

