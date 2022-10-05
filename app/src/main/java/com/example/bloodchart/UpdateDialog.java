package com.example.bloodchart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UpdateDialog extends AppCompatDialogFragment {
    private EditText ed_id, ed_date, ed_time, ed_sbp, ed_dbp;
    private updateDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInsetanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_dialog, null);
        ed_id = view.findViewById(R.id.ed_id);
        ed_date = view.findViewById(R.id.ed_date);
        ed_time = view.findViewById(R.id.ed_time);
        ed_sbp = view.findViewById(R.id.ed_sbp);
        ed_dbp = view.findViewById(R.id.ed_dbp);

        builder.setView(view)
                .setTitle("Record")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = ed_id.getText().toString();
                        String date = ed_date.getText().toString();
                        String time = ed_time.getText().toString();
                        String sbp = ed_sbp.getText().toString();
                        String dbp = ed_dbp.getText().toString();
                        listener.updateTexts(id, date, time, sbp, dbp);
                    }
                });
        return  builder.create();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (updateDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement updateDialogListener");
        }

    }
    public interface updateDialogListener{
        void updateTexts(String ed_id, String ed_date, String ed_time, String ed_sbp, String ed_dbp);
    }
}
