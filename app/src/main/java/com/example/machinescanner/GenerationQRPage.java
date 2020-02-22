package com.example.machinescanner;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;


public class GenerationQRPage extends Fragment {


    public GenerationQRPage() {
        // Required empty public constructor
    }

    EditText nameEditText;
    ImageView qrImage;
    String inputValue;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Button generateBarcodeButton;
    Button saveBarcodeButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generation_qrpage, container, false);


        qrImage = view.findViewById(R.id.qrImageView);
        nameEditText = view.findViewById(R.id.nameEditText);

        generateBarcodeButton = view.findViewById(R.id.generateBarcodeButton);
        generateBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateBarcode();
            }
        });

        saveBarcodeButton = view.findViewById(R.id.saveBarcodeButton);
        saveBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBarcode();
            }
        });

        return view;
    }

    public void generateBarcode() {
        inputValue = nameEditText.getText().toString().trim();
        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            qrgEncoder.setColorBlack(Color.BLACK);
            qrgEncoder.setColorWhite(Color.WHITE);
            try {
                bitmap = qrgEncoder.getBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            nameEditText.setError(getResources().getString(R.string.value_required));
        }
    }

    public void saveBarcode() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                boolean save = new QRGSaver().save(savePath, nameEditText.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                String result = save ? "Image Saved" : "Image Not Saved";
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                nameEditText.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

}
