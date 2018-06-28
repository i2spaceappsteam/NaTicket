package com.NaTicket.n.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.NaTicket.n.buses.IDialogClickListener;


/**
 * Created by Nagarjuna on 1/11/2018.
 */

public class DialogUtils {
    Context context;

    public DialogUtils(Context context) {
        this.context = context;
    }

    public void showNetworkErrorDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Sorry! Not connected to internet")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     *
     * @param message dialog message
     * @param positiveBtnText possible button text
     * @param negativeBtnTxt negative buttont text
     * @param iDialogClickListener listener for click events
     */

    public void showNetworkErrorDialog(String message, String positiveBtnText, String negativeBtnTxt, final IDialogClickListener iDialogClickListener){
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setMessage(message)
                .setCancelable(false);

        if(null != positiveBtnText){
            ad.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    iDialogClickListener.onPositiveButtonCLicked();
                }
            });
        }
        if(null != negativeBtnTxt){
            ad.setNegativeButton(negativeBtnTxt, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    iDialogClickListener.onNegativeButtonCLicked();
                }
            });
        }
        AlertDialog alertDialog = ad.create();
        alertDialog.show();
    }

    public void showErrorMailReportDialog(final String text){
        AlertDialog.Builder ad= new AlertDialog.Builder(context);
        ad.setMessage("Please send this request to developers");
        ad.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmail(text);
            }
        });
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmail(text);
            }
        });
        ad.show();
    }

    private void sendEmail(String message){
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{  "technicalsupport@i2space.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SOAP REQUEST");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        emailIntent.setType("message/rfc822");
        try {
            context.startActivity(Intent.createChooser(emailIntent,
                    "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "No email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}