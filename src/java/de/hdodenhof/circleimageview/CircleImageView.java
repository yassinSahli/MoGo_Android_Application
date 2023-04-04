/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewOutlineProvider
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package de.hdodenhof.circleimageview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import de.hdodenhof.circleimageview.R;

public class CircleImageView
extends ImageView {
    private static final Bitmap.Config BITMAP_CONFIG;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final boolean DEFAULT_BORDER_OVERLAY;
    private static final int DEFAULT_BORDER_WIDTH;
    private static final int DEFAULT_CIRCLE_BACKGROUND_COLOR;
    private static final ImageView.ScaleType SCALE_TYPE;
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private final Paint mBitmapPaint = new Paint();
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBorderColor = -16777216;
    private boolean mBorderOverlay;
    private final Paint mBorderPaint = new Paint();
    private float mBorderRadius;
    private final RectF mBorderRect = new RectF();
    private int mBorderWidth = 0;
    private int mCircleBackgroundColor = 0;
    private final Paint mCircleBackgroundPaint = new Paint();
    private ColorFilter mColorFilter;
    private boolean mDisableCircularTransformation;
    private float mDrawableRadius;
    private final RectF mDrawableRect = new RectF();
    private boolean mReady;
    private boolean mSetupPending;
    private final Matrix mShaderMatrix = new Matrix();

    static {
        SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
        BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    }

    public CircleImageView(Context context) {
        super(context);
        this.init();
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView, n, 0);
        this.mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, 0);
        this.mBorderColor = typedArray.getColor(R.styleable.CircleImageView_civ_border_color, -16777216);
        this.mBorderOverlay = typedArray.getBoolean(R.styleable.CircleImageView_civ_border_overlay, false);
        this.mCircleBackgroundColor = typedArray.getColor(R.styleable.CircleImageView_civ_circle_background_color, 0);
        typedArray.recycle();
        this.init();
    }

    private void applyColorFilter() {
        Paint paint = this.mBitmapPaint;
        if (paint != null) {
            paint.setColorFilter(this.mColorFilter);
        }
    }

    private RectF calculateBounds() {
        int n = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
        int n2 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
        int n3 = Math.min((int)n, (int)n2);
        float f = (float)this.getPaddingLeft() + (float)(n - n3) / 2.0f;
        float f2 = (float)this.getPaddingTop() + (float)(n2 - n3) / 2.0f;
        return new RectF(f, f2, f + (float)n3, f2 + (float)n3);
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable2) {
        if (drawable2 == null) {
            return null;
        }
        if (drawable2 instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable2).getBitmap();
        }
        try {
            Bitmap bitmap = drawable2 instanceof ColorDrawable ? Bitmap.createBitmap((int)2, (int)2, (Bitmap.Config)BITMAP_CONFIG) : Bitmap.createBitmap((int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Bitmap.Config)BITMAP_CONFIG);
            Canvas canvas = new Canvas(bitmap);
            drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable2.draw(canvas);
            return bitmap;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private boolean inTouchableArea(float f, float f2) {
        if (this.mBorderRect.isEmpty()) {
            return true;
        }
        return Math.pow((double)(f - this.mBorderRect.centerX()), (double)2.0) + Math.pow((double)(f2 - this.mBorderRect.centerY()), (double)2.0) <= Math.pow((double)this.mBorderRadius, (double)2.0);
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        this.mReady = true;
        if (Build.VERSION.SDK_INT >= 21) {
            this.setOutlineProvider((ViewOutlineProvider)new OutlineProvider());
        }
        if (this.mSetupPending) {
            this.setup();
            this.mSetupPending = false;
        }
    }

    private void initializeBitmap() {
        this.mBitmap = this.mDisableCircularTransformation ? null : this.getBitmapFromDrawable(this.getDrawable());
        this.setup();
    }

    private void setup() {
        int n;
        if (!this.mReady) {
            this.mSetupPending = true;
            return;
        }
        if (this.getWidth() == 0 && this.getHeight() == 0) {
            return;
        }
        if (this.mBitmap == null) {
            this.invalidate();
            return;
        }
        this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setDither(true);
        this.mBitmapPaint.setFilterBitmap(true);
        this.mBitmapPaint.setShader((Shader)this.mBitmapShader);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor);
        this.mBorderPaint.setStrokeWidth((float)this.mBorderWidth);
        this.mCircleBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mCircleBackgroundPaint.setAntiAlias(true);
        this.mCircleBackgroundPaint.setColor(this.mCircleBackgroundColor);
        this.mBitmapHeight = this.mBitmap.getHeight();
        this.mBitmapWidth = this.mBitmap.getWidth();
        this.mBorderRect.set(this.calculateBounds());
        this.mBorderRadius = Math.min((float)((this.mBorderRect.height() - (float)this.mBorderWidth) / 2.0f), (float)((this.mBorderRect.width() - (float)this.mBorderWidth) / 2.0f));
        this.mDrawableRect.set(this.mBorderRect);
        if (!this.mBorderOverlay && (n = this.mBorderWidth) > 0) {
            this.mDrawableRect.inset((float)n - 1.0f, (float)n - 1.0f);
        }
        this.mDrawableRadius = Math.min((float)(this.mDrawableRect.height() / 2.0f), (float)(this.mDrawableRect.width() / 2.0f));
        this.applyColorFilter();
        this.updateShaderMatrix();
        this.invalidate();
    }

    private void updateShaderMatrix() {
        float f;
        float f2;
        float f3 = 0.0f;
        this.mShaderMatrix.set(null);
        if ((float)this.mBitmapWidth * this.mDrawableRect.height() > this.mDrawableRect.width() * (float)this.mBitmapHeight) {
            f2 = this.mDrawableRect.height() / (float)this.mBitmapHeight;
            f3 = 0.5f * (this.mDrawableRect.width() - f2 * (float)this.mBitmapWidth);
            f = 0.0f;
        } else {
            f2 = this.mDrawableRect.width() / (float)this.mBitmapWidth;
            f = 0.5f * (this.mDrawableRect.height() - f2 * (float)this.mBitmapHeight);
        }
        this.mShaderMatrix.setScale(f2, f2);
        this.mShaderMatrix.postTranslate((float)((int)(f3 + 0.5f)) + this.mDrawableRect.left, (float)((int)(0.5f + f)) + this.mDrawableRect.top);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public int getCircleBackgroundColor() {
        return this.mCircleBackgroundColor;
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public ImageView.ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    public boolean isBorderOverlay() {
        return this.mBorderOverlay;
    }

    public boolean isDisableCircularTransformation() {
        return this.mDisableCircularTransformation;
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }
        if (this.mBitmap == null) {
            return;
        }
        if (this.mCircleBackgroundColor != 0) {
            canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mCircleBackgroundPaint);
        }
        canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mBitmapPaint);
        if (this.mBorderWidth > 0) {
            canvas.drawCircle(this.mBorderRect.centerX(), this.mBorderRect.centerY(), this.mBorderRadius, this.mBorderPaint);
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.setup();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mDisableCircularTransformation) {
            return super.onTouchEvent(motionEvent);
        }
        return this.inTouchableArea(motionEvent.getX(), motionEvent.getY()) && super.onTouchEvent(motionEvent);
    }

    public void setAdjustViewBounds(boolean bl) {
        if (!bl) {
            return;
        }
        throw new IllegalArgumentException("adjustViewBounds not supported.");
    }

    public void setBorderColor(int n) {
        if (n == this.mBorderColor) {
            return;
        }
        this.mBorderColor = n;
        this.mBorderPaint.setColor(n);
        this.invalidate();
    }

    public void setBorderOverlay(boolean bl) {
        if (bl == this.mBorderOverlay) {
            return;
        }
        this.mBorderOverlay = bl;
        this.setup();
    }

    public void setBorderWidth(int n) {
        if (n == this.mBorderWidth) {
            return;
        }
        this.mBorderWidth = n;
        this.setup();
    }

    public void setCircleBackgroundColor(int n) {
        if (n == this.mCircleBackgroundColor) {
            return;
        }
        this.mCircleBackgroundColor = n;
        this.mCircleBackgroundPaint.setColor(n);
        this.invalidate();
    }

    public void setCircleBackgroundColorResource(int n) {
        this.setCircleBackgroundColor(this.getContext().getResources().getColor(n));
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter == this.mColorFilter) {
            return;
        }
        this.mColorFilter = colorFilter;
        this.applyColorFilter();
        this.invalidate();
    }

    public void setDisableCircularTransformation(boolean bl) {
        if (this.mDisableCircularTransformation == bl) {
            return;
        }
        this.mDisableCircularTransformation = bl;
        this.initializeBitmap();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.initializeBitmap();
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        this.initializeBitmap();
    }

    public void setImageResource(int n) {
        super.setImageResource(n);
        this.initializeBitmap();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.initializeBitmap();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        super.setPadding(n, n2, n3, n4);
        this.setup();
    }

    public void setPaddingRelative(int n, int n2, int n3, int n4) {
        super.setPaddingRelative(n, n2, n3, n4);
        this.setup();
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == SCALE_TYPE) {
            return;
        }
        throw new IllegalArgumentException(String.format((String)"ScaleType %s not supported.", (Object[])new Object[]{scaleType}));
    }

    private class OutlineProvider
    extends ViewOutlineProvider {
        private OutlineProvider() {
        }

        public void getOutline(View view, Outline outline) {
            if (CircleImageView.this.mDisableCircularTransformation) {
                ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
                return;
            }
            Rect rect = new Rect();
            CircleImageView.this.mBorderRect.roundOut(rect);
            outline.setRoundRect(rect, (float)rect.width() / 2.0f);
        }
    }

}

