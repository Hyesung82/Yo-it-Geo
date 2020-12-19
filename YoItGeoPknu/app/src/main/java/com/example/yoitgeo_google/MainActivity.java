package com.example.yoitgeo_google;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    class building {
        double positionX, positionY;  // 좌표
        String number;  // e.g. A13
        String name;  // e.g. 누리관

        building(float x, float y, String num, String name) {
            this.positionX = x;
            this.positionY = y;
            this.number = num;
            this.name = name;
        }
    }

    double positions[][] = {{35.134055, 129.103140}, {35.134467, 129.103149},
            {35.134807, 129.103108}, {35.135348, 129.103015}, {35.135456, 129.103823},
            {35.133954, 129.101889}, {35.134290, 129.101522}, {35.134865, 129.101825},
            {35.135181, 129.101166}, {35.135194, 129.101661},  // District A
            {35.1340047970602, 129.10569414737816}, {35.134101332574964, 129.1063543144726},
            {35.13504308401658, 129.10514008522847}, {35.1350760154722, 129.10624714642262},
            {35.1355831496164, 129.1054811148085}, {35.13400689098166, 129.1047937466253},
            {35.1344766342327, 129.1047937420753},  // District B
            {35.13378321969392, 129.1086403001902}, {35.134808724327016, 129.10892408154157},
            {35.13544499247743, 129.1088508780949}, {35.13489853806436, 129.1094458195165},
            {35.133566154843706, 129.10782567156457}, {35.13468149173175, 129.10767924356435},
            {35.135250388253716, 129.10766094815787}, {35.13569951815129, 129.1076060372718},
            {35.13416988725826, 129.10767752237274}, {35.1330646327173, 129.10746869520895},
            {35.13312451387672, 129.1076883679897}, {35.13292727733813, 129.1079246119297}, // C
            {35.132682878219796, 129.10550080511143}, {35.132757734007065, 129.1062788057401},
            {35.13238345474298, 129.10494247887215}, {35.13310954638136, 129.10490585775105}, // D
            {35.131218742658945, 129.1050553361473}, {35.13119645575932, 129.10419137228547},
            {35.13165469348332, 129.10352999527956}, {35.13243113606884, 129.10377953819506},
            {35.132320978343756, 129.10675300333884}, {35.133331071503996, 129.10365795493428},
            {35.13152001543744, 129.1029087583258}, {35.13315935268471, 129.10289041447447},
            {35.13251559340316, 129.1027988994241}, {35.13206643416345, 129.1016639368479},
            {35.13215627026807, 129.10202090187508}, {35.1331144092725, 129.1014991400482},
            {35.1331144092725, 129.1014991400482}, {35.13141522491809, 129.10629533539554},
            {35.132223653614155, 129.10706420123142}};

    String buildingNums[] = {"A11", "A12", "A13", "A15", "A17", "A21", "A22", "A23", "A26", "A27",
    "B11", "B12", "B13", "B14", "B15", "B21", "B22",
            "C11", "C12", "C13", "C14", "C21", "C22", "C23", "C24", "C25", "C26", "C27", "C28",
            "D12", "D13", "D21", "D22",
            "E11", "E12", "E13", "E14", "E17", "E18", "E21", "E22", "E26", "E27", "E28", "E29",
            "E16", "E30", "E31"};
    String buildingNames[] = {"대학본부", "웅비관", "누리관", "향파관", "워커하우스",
    "미래관", "디자인관", "나래관", "부산창업카페", "식품가공공장",  // District A
    "위드센터", "나비센터", "충무관", "환경해양관", "자연과학1관", "가온관", "청운관",  // District B
    "수산질병관리원", "장영실관", "해양공동연구관", "직장어린이집", "수산과학관", "건축관", "호연관",
    "자연과학2관", "인문사회경영관", "해양수산LMO격리사육동", "수조실험동", "아름관",  // District C
    "운동장 본부석", "대운동장", "대학극장", "체육관",  // District D
            "세종1관", "세종2관", "공학1관", "학술정보관",
    "테니스장", "한어울터", "공학2관", "동원 장보고관", "솔동산", "양어장", "양어장 주차장장",
            "양어장관리사", "한솔관", "행복기숙사", "한울관"};

    building sectionA[] = new building[9];


    private GoogleMap mGoogleMap = null;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용
    private static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500;


    private Marker currentMarker = null;
    Location mCurrentLocation;
    LatLng currentPosition;
    private LocationRequest locationRequest;
    private Location location;


//    MarkerOptions[] arrMarkerOptions = new MarkerOptions[1];
    MarkerOptions[] arrMarkerOptions = new MarkerOptions[positions.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // GoogleApiClient의 인스턴스 생성, 지우지 말것!, 현재 위치정보 얻을 때 필요
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            Toast.makeText(this, "현재 위치 표시를 위해 GPS 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
        }


        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

//                String markerTitle = "현재 위치";
//                String markerSnippet = "위도:" + String.valueOf(location.getLatitude()) + " 경도:" + String.valueOf(location.getLongitude());

                // 현재 위치에 마커 생성하고 이동
                //setCurrentLocation(location, markerTitle, markerSnippet);
                int result = checkDistance(location.getLatitude(), location.getLongitude());
                if (result != -1) {
//                    setQuiz(arrMarkerOptions[result].getTitle());
                    mFusedLocationClient.removeLocationUpdates(locationCallback);
                }
                if (result == -1 && checkPermission()) {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }

                mCurrentLocation = location;
            }
        }
    };


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

        if (checkPermission())
            mGoogleMap.setMyLocationEnabled(true);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 승인될 경우
                    System.out.println("권한 승인됨");
                } else {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {


        mGoogleMap = googleMap;

        //지도의 초기위치로 이동
        setDefaultLocation();


        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.baekgyeongee);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 64, 64, false);


        /* 마커 생성 */
//        MarkerOptions markerOption = new MarkerOptions();
//        markerOption
//                .position(new LatLng(positions[0][0], positions[0][1]))
//                .title("A11")
//                .snippet("대학본부");
//        markerOption.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//        googleMap.addMarker(markerOption);

        MarkerOptions markerOptions[] = new MarkerOptions[buildingNums.length];
        for (int i = 0; i < buildingNums.length; i++) {
            Log.d("i 값", String.valueOf(i));
            markerOptions[i] = new MarkerOptions();
            markerOptions[i]
                    .position(new LatLng(positions[i][0], positions[i][1]))
                    .title(buildingNums[i])
                    .snippet(buildingNames[i]);
            markerOptions[i].icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            googleMap.addMarker(markerOptions[i]);
        }

        System.arraycopy(markerOptions, 0, arrMarkerOptions, 0, markerOptions.length);


        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                accessMarker(marker.getTitle());
            }
        });


        startLocationUpdates();
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        accessMarker(marker.getTitle());
//
//        return true;
//    }



    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


//    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
//        if (currentMarker != null) currentMarker.remove();
//
//        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(currentLatLng);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//
//        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.red_leaf_marker);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//        currentMarker = mGoogleMap.addMarker(markerOptions);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
//        mGoogleMap.moveCamera(cameraUpdate);
//
//
//
//        int result = checkDistance(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude);
//        if (result != -1) {
//            accessMarker(arrMarkerOptions[result].getTitle());
//            mFusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//        if (result == -1 && checkPermission()) {
//                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//        }
//    }



    public void setDefaultLocation() {
        //디폴트 위치,
        LatLng DEFAULT_LOCATION = new LatLng(35.134204, 129.105272);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);
    }


    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }



    //GPS 활성화
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) { }
    @Override
    public void onConnectionSuspended(int i) { }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }


    /* 버튼 이벤트 */
    public void showMapImage(View view) {
        MapImageDialog mapImageDialog = new MapImageDialog(this);
        mapImageDialog.setCancelable(false);
        mapImageDialog.show();
    }

    public void showFacility(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.busan.go.kr/geopark/tm0303"));
        startActivity(intent);
        finish();
    }

    public void quiz(View view) {
        Intent intent = new Intent(this, DisplayGameActivity.class);
        startActivity(intent);
    }

//    public void onLastLocationButtonClicked(View view) {
//        // 권한 체크
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
//                return;
//        }
//        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    // 현재 위치
//                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                    mGoogleMap.addMarker(new MarkerOptions()
//                            .position(myLocation)
//                            .title("현재 위치"));
//                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
//                    // 카메라 줌
//                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
//                    System.out.println(myLocation.latitude + ", " + myLocation.longitude);
//                }
//            }
//        });
//
//    }

    public double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        Location startPos = new Location("PointA");
        Location endPos = new Location("PointB");

        startPos.setLatitude(latitude1);
        startPos.setLongitude(longitude1);
        endPos.setLatitude(latitude2);
        endPos.setLongitude(longitude2);


        return startPos.distanceTo(endPos); // 미터
    }

    public int checkDistance(double latitude, double longitude) {
        for (int i=0; i<arrMarkerOptions.length; i++) {
            if (getDistance(latitude, longitude, arrMarkerOptions[i].getPosition().latitude, arrMarkerOptions[i].getPosition().longitude) <= 50) {
                return i;
            }
        }
        return -1;
    }

    public void setDialog(String s) {
        final int id = Integer.parseInt(s);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(arrMarkerOptions[id].getSnippet() + "에 대한 설명을 보시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), arrMarkerOptions[id].getSnippet() + " 설명 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), DisplaySubCommentActivity.class);

                        intent.putExtra("id", id);

                        startActivity(intent);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void setQuiz(String s) {
        final String str = s;
        final int id = Integer.parseInt(s);

        final String[][] quiz = {{"ㄴㄹㄱ", "누리관"},
                {"ㄷㄱㄱㅁ", "돌개구멍"},
                {"ㄱㄹㄱㅅ", "구리광산"},
                {"ㅎㅅㄷㄱ", "해식동굴"},
                {"ㅎㄱㅅㅅㅇㅁ", "함각섬석암맥"},
                {"ㅎㅅㄱㄹㅇ", "화산각력암"},
                {"ㅂㅇ", "벽옥"},
                {"ㅇㅎㅈㅌㅈㅇㅊ", "응회질퇴적암층"},
                {"ㅎㅍㄱ", "향파관"}};

        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("초성 퀴즈");
        builder.setMessage("현재 이 곳의 명칭은?\n" + "힌트 : " + quiz[id][0]);
        builder.setView(editText);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String answer = editText.getText().toString();
                        if (answer.equals(quiz[id][1])) {
                            Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_SHORT).show();

                            setDialog(str);
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "취소를 선택했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void accessMarker(String s) {
        int id = 0;
        for(int i= 0; i < arrMarkerOptions.length; i++) {
            if(buildingNums[i].equals(s)) {
                id = i;
                break;
            }
        }

        Toast.makeText(getApplicationContext(), arrMarkerOptions[id].getSnippet() + " 설명 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), DisplaySubCommentActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }









///////////////////////게임하기
//    public void GameOver(){
//        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
//        alertDialogBuilder
//                .setMessage("게임 종료")
//                .setCancelable(false)
//                .setPositiveButton("새 게임", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int whichbutton){
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    }
//                })
//                .setNegativeButton("나가기", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int whichbutton) {
//                        System.exit(0);
//                    }
//                });
//        alertDialogBuilder.show();
//
//    }
//    public void NextQuestion(int num){
//        tv_question.setText(question.getQuestion(num));
//        btn.setText(question.getchoice1(num));
//        btn2.setText(question.getchoice2(num));
//
//        answer = question.getCorrAnswer(num);
//    }
//
//    //@Override
//    public void onClick(View v){
//        switch(v.getId()){
//            case R.id.btn:
//                if(btn.getText()==answer){
//                    Toast.makeText(this,"정답입니다.", Toast.LENGTH_SHORT).show();
//                    NextQuestion(random.nextInt(questionLength));
//                }else{
//                    GameOver();
//                }
//                break;
//
//            case R.id.btn2:
//                if(btn2.getText()==answer){
//                    Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show();
//                    NextQuestion(random.nextInt(questionLength));
//                }else{
//                    GameOver();
//                }
//                break;
//        }
//
//
//
//    }

}
