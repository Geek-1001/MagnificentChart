package com.hornet.magnificentchartdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.List;

/**
 * Created by Ahmed on 30.01.14.
 */
public class MagnificentChart extends View {

// #MARK - Constants

    // default initialization params
    public static final float ANIMATION_SPEED_DEFAULT = 6.5f;
    public static final float ANIMATION_SPEED_SLOW = 1.0f;
    public static final float ANIMATION_SPEED_FAST = 10.0f;
    public static final float ANIMATION_SPEED_NORMAL = 3.5f;

    // view properties
    private List<MagnificentChartItem> chartItemsList;
    private int maxValue;
    private boolean isTitleShowing;
    private boolean isAnimated;
    private boolean isRound;
    private boolean isShadowShowing;
    private int shadowBackgroundColor;
    private int chartBackgroundColor;
    private Context context;
    private Typeface typeface = null;
    private int width;
    private int height;
    private float animationSpeed = 6.5f;

    private float globalCurrentAngle = 0.0f;



// #MARK - Constructors

    public MagnificentChart(Context context) {
        super(context);
        init(context, null, 0, false, false, true, false, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public MagnificentChart(Context context, List<MagnificentChartItem> itemsList, int maxValue){
        super(context);
        init(context, itemsList, maxValue, false, false, true, false, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public MagnificentChart(Context context, List<MagnificentChartItem> itemsList, int maxValue, boolean isAnimated, boolean isRound, boolean showShadow, boolean showTitle){
        super(context);
        init(context, itemsList, maxValue, isAnimated, isRound, showShadow, showTitle, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public MagnificentChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MagnificentChart, 0, 0);
        try {
            boolean isAnimated = typedArray.getBoolean(R.styleable.MagnificentChart_animation, false);
            boolean isRound = typedArray.getBoolean(R.styleable.MagnificentChart_round, false);
            boolean showShadow = typedArray.getBoolean(R.styleable.MagnificentChart_shadow, true);
            boolean showTitle = typedArray.getBoolean(R.styleable.MagnificentChart_showTitle, false);
            int shadowColor = typedArray.getColor(R.styleable.MagnificentChart_shadowColor, Color.parseColor("#F2F2F2"));
            int backgroundColor = typedArray.getColor(R.styleable.MagnificentChart_background, Color.parseColor("#FFFFFF"));

            init(context, null, 0, isAnimated, isRound, showShadow, showTitle, shadowColor, backgroundColor);
        } finally {
            typedArray.recycle();
        }
    }

// #MARK - Override class methods

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.save();
        if(width != height){
            return;
        }

        if(this.isAnimated){
            animatedDraw(canvas);
        } else {
            regularDraw(canvas);
        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged (int width, int height, int oldWidth, int oldHeight){
        this.width = width;
        this.height = height;
    }

// #MARK - Custom methods

    private void init(Context context, List<MagnificentChartItem> itemsList, int maxValue, boolean isAnimated, boolean isRound, boolean showShadow, boolean showTitle, int shadowColor, int backgroundColor){
        this.context = context;
        this.chartItemsList = itemsList;
        this.isAnimated = isAnimated;
        this.isRound = isRound;
        this.isShadowShowing = showShadow;
        this.shadowBackgroundColor = shadowColor;
        this.chartBackgroundColor = backgroundColor;
        this.maxValue = maxValue;
        this.isTitleShowing = showTitle;
    }

    private int measureWidth(int widthMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        } else {
            result = screenWidth;
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        this.width = result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        } else {
            result = screenHeight;
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result, specSize);
            }
        }
        this.height = result;
        return result;
    }


    public void setAnimationState(boolean state){
        this.isAnimated = state;
        invalidate();
    }

    public void setAnimationSpeed(float animationSpeed){ // use just value ANIMATION_SPEED_... from current class
        if(animationSpeed == ANIMATION_SPEED_DEFAULT || animationSpeed == ANIMATION_SPEED_SLOW || animationSpeed == ANIMATION_SPEED_FAST || animationSpeed == ANIMATION_SPEED_NORMAL){
            this.animationSpeed = animationSpeed;
        } else {
            this.animationSpeed = ANIMATION_SPEED_DEFAULT;
        }
        invalidate();
    }

    public void setRound(boolean state){
        this.isRound = state;
        invalidate();
    }

    public void setShadowShowingState(boolean state){
        this.isShadowShowing = state;
        invalidate();
    }

    public boolean getAnimationState(){
        return this.isAnimated;
    }

    public boolean getRound(){
        return this.isRound;
    }

    public boolean getShadowShowingState(){
        return this.isShadowShowing;
    }

    public void setTitleShowingState(boolean state){
        this.isTitleShowing = state;
        invalidate();
    }

    public void setTypeface(Typeface typeface){
        this.typeface = typeface;
        invalidate();
    }

    public void setChartItemsList(List<MagnificentChartItem> itemsList){
        this.chartItemsList = itemsList;
        invalidate();
    }

    public void setShadowBackgroundColor(int color){
        this.shadowBackgroundColor = color;
        invalidate();
    }

    public void setChartBackgroundColor(int color){
        this.chartBackgroundColor = color;
        invalidate();
    }

    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;
        invalidate();
    }

    private float getPercent(int value, int maxValue){
        float result = (float) value/maxValue;
        return result;
    }

// #MARK - Drawing Methods

    private void regularDraw(Canvas canvas){
        Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(shadowBackgroundColor);
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(chartBackgroundColor);
        RectF rect = new RectF();
        rect.set(10, 10, width - 10, height - 10);
        drawMainCircle(canvas, insideShadowPaint, insideChartPaint, rect);
        canvas.rotate(-90f, rect.centerX(), rect.centerY());

        if(this.chartItemsList != null && this.maxValue > 0){
            drawItems(canvas, rect);
            canvas.rotate(90f, rect.centerX(), rect.centerY());
            drawInsideCircle(canvas, insideShadowPaint, insideChartPaint);
        }
    }

    private void animatedDraw(Canvas canvas){
        Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(shadowBackgroundColor);
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(chartBackgroundColor);
        RectF rect = new RectF();
        rect.set(10, 10, width - 10, height - 10);
        drawMainCircle(canvas, insideShadowPaint, insideChartPaint, rect);
        canvas.rotate(-90f, rect.centerX(), rect.centerY());

        if(chartItemsList != null && maxValue > 0){
            drawItems(canvas, rect);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(chartBackgroundColor);
            RectF oval = new RectF();
            oval.set(8, 8, width - 8, height - 8);
            Path path = new Path();
            path.moveTo(oval.centerX(), oval.centerY());
            path.addArc(oval, globalCurrentAngle, 360.0f - globalCurrentAngle);
            path.lineTo(oval.centerX(), oval.centerY());

            canvas.drawPath(path, paint);
            globalCurrentAngle += animationSpeed; // <-- animation speed

            canvas.rotate(90f, rect.centerX(), rect.centerY());
            if(!isRound){
                if(isShadowShowing){
                    canvas.drawCircle(width/2, height/2, width/4 - 10, insideShadowPaint);
                }
                canvas.drawCircle(width/2, height/2, width/4 - 20, insideChartPaint);
            }

            if(globalCurrentAngle >= 360){
                globalCurrentAngle = 0.0f;
                canvas.rotate(-90f, rect.centerX(), rect.centerY());
                drawItems(canvas, rect);
                canvas.rotate(90f, rect.centerX(), rect.centerY());
                drawInsideCircle(canvas, insideShadowPaint, insideChartPaint);
                return;
            }
            invalidate();
        }
    }

    private void drawInsideCircle(Canvas canvas, Paint insideShadowPaint, Paint insideChartPaint){
        if(!isRound){
            if(isShadowShowing){
                canvas.drawCircle(width/2, height/2, width/4 - 10, insideShadowPaint);
            }
            canvas.drawCircle(width/2, height/2, width/4 - 20, insideChartPaint);
        }
    }

    private void drawMainCircle(Canvas canvas, Paint insideShadowPaint, Paint insideChartPaint, RectF mainRectangle){
        if(isShadowShowing){
            canvas.drawCircle(width/2, height/2, width/2, insideShadowPaint);
        }
        canvas.drawArc(mainRectangle, 0f, 360f, true, insideChartPaint);
    }

    private void drawItems(Canvas canvas, RectF mainRectangle){
        float startAngle = 0f;
        float anglesSum = 0f;
        Paint currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        for(int i = 0; i < chartItemsList.size(); ++i){
            MagnificentChartItem currentItem = chartItemsList.get(i);
            int color = currentItem.color;
            String title = currentItem.title;
            int value = currentItem.value;
            float currentPercentValue = getPercent(value, maxValue);
            float currentAngle = currentPercentValue * 360;
            anglesSum += currentAngle;
            currentPaint.setColor(color);
            canvas.drawArc(mainRectangle, startAngle, currentAngle, true, currentPaint);
            startAngle += currentAngle;
        }
    }

}
