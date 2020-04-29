package com.spreadtracker.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * {@link SplashActivity} serves as the entry point to the application.
 * It could show a splash image (such as the app logo), but ultimately
 * its purpose is to validate any necessary user permissions and to route
 * the user to the next activity. For that reason,
 */
public class SplashActivity extends Activity {

    private static final int RC_CHECK_PERMISSIONS = 123;

    private @NonNull String[] mCheckingPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.BLUETOOTH};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validatePermissions();
    }

    // Goes to the next desired activity
    private void advance () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void validatePermissions () {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        for (String permission : mCheckingPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                permissionsNeeded.add(permission);
        }

        mCheckingPermissions = new String[permissionsNeeded.size()];
        permissionsNeeded.toArray(mCheckingPermissions);

        if (mCheckingPermissions.length == 0) {
            advance(); // User doesn't need to grant any permissions, so continue to next screen
            return;
        }
        ActivityCompat.requestPermissions(this, mCheckingPermissions, RC_CHECK_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RC_CHECK_PERMISSIONS) return;

        // This method will assume that each of the requested permissions is
        // a requirement. If the user cancels the permission request or denies a request to grant a permission,
        // this will close the application. This behaviour can be changed later.
        if (grantResults.length == 0) {
            finish();
            return;
        }

        for (int result : grantResults)
            if (result == PackageManager.PERMISSION_DENIED) {
                finish();
                return;
            }

        advance(); // User granted all permission requests, so continue to next screen
    }
}
