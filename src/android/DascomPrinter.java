package com.chinacreator.cordova.plugin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.dascom.print.PrintCommands.ZPL;
import com.dascom.print.Transmission.BluetoothPipe;
import com.dascom.print.Transmission.Pipe;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import static com.dascom.print.Utils.Unit.DPI_203.CM;
import static com.dascom.print.Utils.Unit.DPI_203.MM;

public class DascomPrinter extends CordovaPlugin {

    private Pipe pipe;
    private ZPL printer;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "connectPrinter":
                String address = args.getString(0);
                this.connectPrinter(address, callbackContext);
                break;
            case "disconnectPrinter":
                this.disconnectPrinter(callbackContext);
                break;
            case "printImage":
                String img = args.getString(0);
                this.printImage(img, callbackContext);
                break;
        }
        return false;
    }

    private void connectPrinter(String address, CallbackContext callbackContext) {
        if (pipe != null) {
            pipe.close();
            pipe = null;
        }
        try {
            pipe = new BluetoothPipe(address);
            printer = new ZPL(pipe);
            callbackContext.success("链接成功");
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error("链接失败：" + e.getMessage());
        }
    }

    private void disconnectPrinter(CallbackContext callbackContext) {
        if (pipe != null) {
            pipe.close();
            pipe = null;
        }
        callbackContext.success("断开成功");
    }

    private void printImage(String img, CallbackContext callbackContext) {
        byte[] bytes = Base64.decode(img.split(",")[1], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        printer.setLabelStart();
        printer.setLabelWidth(bitmap.getWidth());
        printer.setLabelLength(bitmap.getHeight() + 30);
        printer.printBitmap(0, 0, bitmap);
        boolean b = printer.setLabelEnd();
        if (b) {
            callbackContext.success("发送成功");
        } else {
            callbackContext.error("发送失败");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pipe != null) {
            pipe.close();
            pipe = null;
        }
    }
}
