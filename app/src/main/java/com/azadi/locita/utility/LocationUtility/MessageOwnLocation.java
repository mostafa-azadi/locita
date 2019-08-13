package com.azadi.locita.utility.LocationUtility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.azadi.locita.R;


/**
 * Created by Mostafa on 09/13/18.
 */

public class MessageOwnLocation {

    public static void GpsOnDialog(final Context context, String title, String note){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,
                R.style.AppCompatAlertDialogStyle);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(note);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GpsUtility gpsUtility = new GpsUtility(context);
                gpsUtility.gpsSetting();
                System.exit(1);
            }
        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //((Activity) context).finish();
                System.exit(1);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        //Change Direction to RTL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        try {
            alertDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void GooglePlayUpdateDialog(final Context context, String title, String note){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context,
                R.style.AppCompatAlertDialogStyle);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(note);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                DownloadGpsTask downloadTask = new DownloadGpsTask(context);
                downloadTask.execute();
                 */
                final String appPackageName = "com.google.android.gms";
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                System.exit(1);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        //Change Direction to RTL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        alertDialog.show();
    }
}
