package com.challenge.concrete.concretegithubrepolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by eliete on 7/26/16.
 */
public class AlertDialogFragment extends android.support.v4.app.DialogFragment {

    public static final String MESSAGE = "message";
    private String message;

    public static AlertDialogFragment newInstance(String message){
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle b = new Bundle();
        b.putString(MESSAGE, message);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            message = savedInstanceState.getString(MESSAGE);
        }else{
            message = getArguments().getString(MESSAGE);
        }

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setTitle(getResources().getString(R.string.app_name));
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return alert.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MESSAGE, message);
    }
}
