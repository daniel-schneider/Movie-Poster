package com.popular.movies.popularmovies.view.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class RoundedTransformation implements Transformation {
    private final int mRadius;
    private final int mMargin;

    public RoundedTransformation(int radius, int margin) {
        mRadius = radius;
        mMargin = margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        int radius = 0;

        if (mRadius != 0) {
            radius = source.getHeight() / mRadius;
        }

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(mMargin, mMargin, source.getWidth() - mMargin, source.getHeight() - mMargin), radius, radius, paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return "Rounded radius: " + mRadius + " margin: " + mMargin;
    }
}
