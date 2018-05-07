package com.popular.movies.popularmovies.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.popular.movies.popularmovies.view.transformation.BlurTransformation;
import com.popular.movies.popularmovies.view.transformation.RoundedTransformation;
import com.squareup.picasso.Picasso;



public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    public static void loadImage(Context context, String url, ImageView imageView) {
        if (url != null && !url.isEmpty()) {
            Picasso.with(context).load(url).into(imageView);
        }
    }

    public static void loadImageWithPlaceholder(Context context, String url, ImageView imageView, int placeholder) {
        loadRoundedImageWithPlaceholder(context, url, imageView, placeholder, 0);
    }

    public static void loadRoundedImageWithPlaceholder(Context context, String url, ImageView imageView, int placeholder) {
        loadRoundedImageWithPlaceholder(context, url, imageView, placeholder, 20);
    }

    private static void loadRoundedImageWithPlaceholder(Context context, String url, ImageView imageView, int placeholder, int radius) {
        if (url != null && !url.isEmpty() && !url.equalsIgnoreCase(" ")) {
            Picasso.with(context).load(url).placeholder(placeholder).transform(new RoundedTransformation(radius, 0)).into(imageView);
        } else {
            Picasso.with(context).load(placeholder).transform(new RoundedTransformation(radius, 0)).into(imageView);
        }
    }

    public static void loadBlurredImage(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).transform(new BlurTransformation(context)).centerCrop().fit().into(imageView);
    }

    public static void loadRoundedFitImageWithPlaceholder(Context context, String url, ImageView imageView, int placeholder, int radius) {
        if (url != null && !url.isEmpty() && !url.equalsIgnoreCase(" ")) {
            Picasso.with(context).load(url).placeholder(roundPlaceholder(context, placeholder, radius)).transform(new RoundedTransformation(radius, 0)).centerCrop().fit().into(imageView);
        } else {
            Picasso.with(context).load(placeholder).transform(new RoundedTransformation(radius, 0)).centerCrop().fit().into(imageView);
        }
    }

    public static void loadRoundedStretchedImageWithPlaceholder(Context context, String url, ImageView imageView, int placeholder, int radius) {
        if (url != null && !url.isEmpty() && !url.equalsIgnoreCase(" ")) {
            Picasso.with(context).load(url).placeholder(roundPlaceholder(context, placeholder, radius)).transform(new RoundedTransformation(radius, 0)).fit().into(imageView);
        } else {
            Picasso.with(context).load(placeholder).transform(new RoundedTransformation(radius, 0)).fit().into(imageView);
        }
    }

    private static Drawable roundPlaceholder(Context context, int placeholderId, int radius) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), placeholderId, options);

        if (radius != 0) {
            radius = bitmap.getHeight() / radius;
        }

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), radius, radius, paint);

        if (bitmap != output) {
            bitmap.recycle();
        }

        return new BitmapDrawable(context.getResources(), output);
    }

}
