package de.androidcrypto.blelistdevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvAddress;
    public BluetoothAdapter btAdapter;
    public BluetoothManager btManager;

    /**
     * This block is for requesting permissions up to Android 12+
     *
     */
    private final int ENABLE_BT_REQUEST = 190;
    private static final int PERMISSIONS_REQUEST_CODE = 191;
    private static final String[] BLE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    @SuppressLint("InlinedApi")
    private static final String[] ANDROID_12_BLE_PERMISSIONS = new String[]{
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static void requestBlePermissions(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            ActivityCompat.requestPermissions(activity, ANDROID_12_BLE_PERMISSIONS, requestCode);
        else
            ActivityCompat.requestPermissions(activity, BLE_PERMISSIONS, requestCode);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestBlePermissions(this, PERMISSIONS_REQUEST_CODE);

        // inflate option menu
        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        tvAddress = findViewById(R.id.tvMainAddress);

        // Initializes Bluetooth adapter.
        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        // displays a dialog requesting user permission to enable Bluetooth.
        if (btAdapter == null || !btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST);
        }

        Button scan = findViewById(R.id.btnMainScan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeDeviceScanActivityOwn.class);
                startActivity(intent);
            }
        });

        Button scanEnhanced = findViewById(R.id.btnMainScanBleEnhanced);
        scanEnhanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeDeviceScanEnhancedActivityOwn.class);
                startActivity(intent);
            }
        });
    }
}