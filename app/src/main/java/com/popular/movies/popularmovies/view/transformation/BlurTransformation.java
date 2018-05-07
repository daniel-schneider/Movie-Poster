package com.popular.movies.popularmovies.view.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

import java.lang.ref.WeakReference;

public class BlurTransformation implements Transformation {
    private WeakReference<Context> mContext;

    public BlurTransformation(Context context) {
        super();
        mContext = new WeakReference<>(context);
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        RenderScript rs = RenderScript.create(mContext.get());
        int width = Math.round(bitmap.getWidth() * 0.1f);
        int height = Math.round(bitmap.getHeight() * 0.1f);

        Bitmap source = Bitmap.createScaledBitmap(bitmap, width, height, false);

        if (source == null) {
            return null;
        }

        Allocation input = Allocation.createFromBitmap(rs, source,
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(25);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(source);

        input.destroy();
        output.destroy();
        script.destroy();

        bitmap.recycle();

        return source;
    }

    @Override
    public String key() {
        return "Blurred Image";
    }
}
