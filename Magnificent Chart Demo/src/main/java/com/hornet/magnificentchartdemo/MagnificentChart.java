package com.hornet.magnificentchartdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    // view properties
    private List<MagnificentChartItem> chartItemsList;
    private int maxValue;
    private boolean isAnimated;
    private boolean isRound;
    private boolean isShadowShowing;
    private int shadowBackgroundColor;
    private int chartBackgroundColor;
    private Context context;
    private Typeface typeface = null;

    private int width;
    private int height;

    // other

// #MARK - Constructors

    public MagnificentChart(Context context) {
        super(context);
        init(context, null, 0, false, false, true, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public MagnificentChart(Context context, List<MagnificentChartItem> itemsList, int maxValue){
        super(context);
        init(context, itemsList, maxValue, false, false, true, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public MagnificentChart(Context context, List<MagnificentChartItem> itemsList, int maxValue, boolean isAnimated, boolean isRound, boolean showShadow){
        super(context);
        init(context, itemsList, maxValue, isAnimated, isRound, showShadow, Color.parseColor("#DDDDDD"), Color.parseColor("#FFFFFF"));
    }

    public MagnificentChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MagnificentChart, 0, 0);
        try {
            boolean isAnimated = typedArray.getBoolean(R.styleable.MagnificentChart_animation, false);
            boolean isRound = typedArray.getBoolean(R.styleable.MagnificentChart_round, false);
            boolean showShadow = typedArray.getBoolean(R.styleable.MagnificentChart_shadow, true);
            int shadowColor = typedArray.getColor(R.styleable.MagnificentChart_shadowColor, Color.parseColor("#F2F2F2"));
            int backgroundColor = typedArray.getColor(R.styleable.MagnificentChart_background, Color.parseColor("#FFFFFF"));

            init(context, null, 0, isAnimated, isRound, showShadow, shadowColor, backgroundColor);
        } finally {
            typedArray.recycle();
        }
    }

// #MARK - Override class methods

    @Override
    protected void onDraw(Canvas canvas){
        if(width != height){
            return;
        }
        Paint insideShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideShadowPaint.setColor(this.shadowBackgroundColor);
        if(this.isShadowShowing){
            canvas.drawCircle(width/2, height/2, width/2, insideShadowPaint);
        }
        Paint insideChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        insideChartPaint.setColor(this.chartBackgroundColor);
        RectF rect = new RectF();
        rect.set(10, 10, width - 10, height - 10);
        canvas.drawArc(rect, 0f, 0f + 360, true, insideChartPaint);
        canvas.rotate(-90f, rect.centerX(), rect.centerY());

        float startAngle = 0f;
        float anglesSum = 0f;

        if(this.chartItemsList != null){
            Paint currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            for(int i = 0; i < chartItemsList.size(); ++i){
                MagnificentChartItem currentItem = chartItemsList.get(i);
                int color = currentItem.color;
                String title = currentItem.title;
                int value = currentItem.value;
                float currentPercentValue = getPercent(value, this.maxValue);
                float currentAngle = currentPercentValue * 360;
                anglesSum += currentAngle;

                if(anglesSum != 360 && anglesSum < 360){
                    currentPaint.setColor(color);
                    canvas.drawArc(rect, startAngle, currentAngle, true, currentPaint);
                    startAngle += currentAngle;
                }
            }

            if(!this.isRound){
                canvas.rotate(90f, rect.centerX(), rect.centerY());
                if(this.isShadowShowing){
                    canvas.drawCircle(width/2, height/2, width/4 - 10, insideShadowPaint);
                }
                canvas.drawCircle(width/2, height/2, width/4 - 20, insideChartPaint);
            }
        }

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

    private void init(Context context, List<MagnificentChartItem> itemsList, int maxValue, boolean isAnimated, boolean isRound, boolean showShadow, int shadowColor, int backgroundColor){

        this.context = context;
        this.chartItemsList = itemsList;
        this.isAnimated = isAnimated;
        this.isRound = isRound;
        this.isShadowShowing = showShadow;
        this.shadowBackgroundColor = shadowColor;
        this.chartBackgroundColor = backgroundColor;
        this.maxValue = maxValue;

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
    }

    public void setRound(boolean state){
        this.isRound = state;
    }

    public void setShadowShowingState(boolean state){
        this.isShadowShowing = state;
    }

    public void setTypeface(Typeface typeface){
        this.typeface = typeface;
    }

    public void setChartItemsList(List<MagnificentChartItem> itemsList){
        this.chartItemsList = itemsList;
    }

    public void setShadowBackgroundColor(int color){
        this.shadowBackgroundColor = color;
    }

    public void setChartBackgroundColor(int color){
        this.chartBackgroundColor = color;
    }

    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;
    }

    private float getPercent(int value, int maxValue){
        float result = (float) value/maxValue;
        return result;
    }

}
