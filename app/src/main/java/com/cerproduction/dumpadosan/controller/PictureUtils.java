package com.cerproduction.dumpadosan.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * @author Lazar Cerovic (2020), some parts of this class were borrowed from the course book:
 *  "Android Programming: The Big Nerd Ranch Guide" by Bill Phillips & Brian Hardy
 *  This class handles the downscaling of the picture taken in AddGoalsActivity.java
 */
public class PictureUtils {

    /**
     * This static method takes a filepath and an activity and returns a resized (rescaled) bitmap
     * @param filePath
     * @param a
     * @return Bitmap
     */
    public static Bitmap getScaledBitmap(String filePath, Activity a) {
        Point point = new Point();
        a.getWindowManager().getDefaultDisplay()
                .getSize(point);
        return getScaledBitmap(filePath, point.x, point.y);
    }


    /**
     * This method reads the dimensions of the picture on the disk, as well as scaling down
     * the picture to fit usage. Lastly, it reads and creates the final bitmap
     * @param filePath
     * @param dw
     * @param dh
     * @return Bitmap
     */
    public static Bitmap getScaledBitmap(String filePath, int dw, int dh) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opt);

        float sw = opt.outWidth;
        float sh = opt.outHeight;

        // Figure out how much to scale down by
        int iss = 1;
        if (sh > dh || sw > dw) {
            float hScale = sh / dh;
            float wScale = sw / dw;

            iss = Math.round(hScale > wScale ? hScale :
                    wScale);
        }

        opt = new BitmapFactory.Options();
        opt.inSampleSize = iss;

        return BitmapFactory.decodeFile(filePath, opt);
    }

}
