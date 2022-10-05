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

public class DeleteDialog extends AppCompatDialogFragment {
    private EditText ed_id, ed_date, ed_time, ed_sbp, ed_dbp;
    private deleteDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInsetanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_dialog, null);
        ed_id = view.findViewById(R.id.ed_id);

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
                        listener.deleteTexts(id);
                    }
                });
        return  builder.create();
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            listener = (deleteDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement deleteDialogListener");
        }

    }
    public interface deleteDialogListener{
        void deleteTexts(String ed_id);
    }
}
