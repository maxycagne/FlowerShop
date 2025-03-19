package com.example.flowershop;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class ImageHelper {

    public static Drawable getImg(Context context, String flowerName)
    {
        int resourcesId = 0;

        switch (flowerName)
        {
            case "Daisy":
                resourcesId = R.drawable.img_daisy;
                break;
            case "Rose":
                resourcesId = R.drawable.img_rose;
                break;
            case "Sunflower":
                resourcesId = R.drawable.img_sf;
                break;
            case "Tulip":
                resourcesId = R.drawable.img_tulips;
                break;
            case "Peony":
                resourcesId = R.drawable.img_peony;
                break;
            default:
                resourcesId = R.drawable.img_tulips;
                break;
        }
        return ContextCompat.getDrawable(context,resourcesId);
    }
}
