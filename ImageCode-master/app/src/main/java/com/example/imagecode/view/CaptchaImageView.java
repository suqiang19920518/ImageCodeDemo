package com.example.imagecode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Random;

/**
 * @author: sq
 * @date: 2017/9/6
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 自定义控件——图像验证码
 */
public class CaptchaImageView extends ImageView {

    private int width, height;
    private int captchaLength = 6;//验证码长度,此处默认为6
    private int captchaType = CaptchaGenerator.NUMBERS;//验证码格式，此处默认为纯数字
    private boolean isRedraw;
    private boolean isDot;//是否显示点阴影，此处默认不显示
    private CaptchaGenerator.Captcha generatedCaptcha;

    public CaptchaImageView(Context context) {
        super(context);
    }

    public CaptchaImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    /**
     * Regenerates the captcha again to result a new bitmap and captcha code
     */
    public void regenerate() {
        reDraw();
    }

    /**
     * Redraws the captcha
     */
    private void reDraw() {
        draw(width, height);
        setImageBitmap(generatedCaptcha.getBitmap());
    }

    private void draw(int width, int height) {
        generatedCaptcha = CaptchaGenerator.regenerate(width, height, captchaLength, captchaType, isDot);
    }


    /**
     * 图片验证码生成器
     */
    public static class CaptchaGenerator {

        public static final int ALPHABETS = 1, NUMBERS = 2, BOTH = 3;

        private static Captcha regenerate(int width, int height, int length, int type, boolean isDot) {

            /**
             * 创建画笔1，用于绘制矩形边框
             */
            Paint border = new Paint();
            // 设置画笔的风格为“空心”，即描边
            border.setStyle(Paint.Style.STROKE);
            // 设置画笔颜色，即空心边框颜色
            border.setColor(Config.COLOR_BORDER);

            /**
             * 创建画笔2，用于绘制直线、验证码文本
             */
            Paint paint = new Paint();
            // 设置画笔颜色
            paint.setColor(Config.COLOR_TEXT);
            // 设置画笔的风格为“实心”和“空心”，即填充内部和描边
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            if (isDot)
                paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体样式，加粗字体
            else
                paint.setTypeface(Typeface.MONOSPACE); //设置字体样式，monospace字体

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);//创建画布
            canvas.drawColor(Config.COLOR_CANVAS);//设置画布背景颜色

            //绘制矩形
            canvas.drawRect(0, 0, width - 1, height - 1, border);

            //绘制验证码文本（随机）
            String generatedText = drawRandomText(canvas, paint, width, height, length, type, isDot);

            int textX;
            int textY = height / 2;

            //绘制直线
            if (isDot) {
                textX = (width - length * Config.LARGER_WIDTH_TEXT) / 2;//此处每个字符的宽度为25
                for (int i = 0; i < Config.LINE_NUM; i++) {
                    canvas.drawLine(textX - 10, textY - generateRandomInt(7, 10), width - textX + 10, textY - generateRandomInt(5, 10), paint);
                }
            } else {
                textX = (width - length * Config.SMALL_WIDTH_TEXT) / 2;//此处每个字符的宽度为20
                for (int i = 0; i < Config.LINE_NUM; i++) {
                    canvas.drawLine(textX - 8, textY - generateRandomInt(7, 10), width - textX + 8, textY - generateRandomInt(5, 10), paint);
                }
            }

            // 绘制小圆点
            int[] point;
            if (isDot) {
                Random random = new Random();
                for (int i = 0; i < Config.POINT_NUM; i++) {
                    point = getPoint(height, width);
                    canvas.drawCircle(point[0], point[1], random.nextInt(3)+1, paint);//画点
                }
            }

            return (new Captcha(generatedText, bitmap));
        }

        /**
         * 随机产生点的圆心点坐标
         *
         * @param height
         * @param width
         * @return
         */
        public static int[] getPoint(int height, int width) {
            int[] tempCheckNum = {0, 0, 0, 0};
            tempCheckNum[0] = (int) (Math.random() * width);
            tempCheckNum[1] = (int) (Math.random() * height);
            return tempCheckNum;
        }


        /**
         * 绘制随机文本，并返回文本内容
         */
        private static String drawRandomText(Canvas canvas, Paint paint, int width, int height, int length, int type, boolean isDot) {
            String generatedCaptcha = "";
            float[] scewRange = {-0.5f, 0.5f};
            Random random = new Random();
            //设置画笔的倾斜因子,正数表示向左倾斜，负数表示向右倾斜，值越大，倾斜程度越大
            paint.setTextSkewX(scewRange[random.nextInt(scewRange.length)]);

            for (int index = 0; index < length; index++) {
                String temp = generateRandomText(type);
                generatedCaptcha = generatedCaptcha + temp;
                //设置画笔字体大小
                paint.setTextSize(Config.TEXT_SIZE_RANGE[random.nextInt(Config.TEXT_SIZE_RANGE.length)]);
//                paint.setTextSize(42);//指定画笔字体大小，为固定值

                //设置文本baseline在屏幕上的位置，使其垂直居中
                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                int baseline = (height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
                int textX;

                //绘制文本
                if (isDot) {
                    textX = (width - length * Config.LARGER_WIDTH_TEXT) / 2;//此处每个字符的宽度为25
                    canvas.drawText(temp, textX + (index * Config.LARGER_WIDTH_TEXT), baseline, paint);
                } else {
                    textX = (width - length * Config.SMALL_WIDTH_TEXT) / 2;//此处每个字符的宽度为20
                    canvas.drawText(temp, textX + (index * Config.SMALL_WIDTH_TEXT), baseline, paint);
                }
            }
            return generatedCaptcha;
        }

        /**
         * 生成随机文本
         *
         * @param type
         * @return
         */
        private static String generateRandomText(int type) {
            String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] alphabets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
                    , "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            Random random = new Random();
            Random mixedRandom = new Random();
            String temp;
            if (type == ALPHABETS)
                temp = alphabets[random.nextInt(alphabets.length)];
            else if (type == NUMBERS)
                temp = numbers[random.nextInt(numbers.length)];
            else
                temp = (mixedRandom.nextBoolean()) ? (alphabets[random.nextInt(alphabets.length)]) : (numbers[random.nextInt(numbers.length)]);
            return temp;
        }

        private static int generateRandomInt(int length) {
            Random random = new Random();
            int ran = random.nextInt(length);
            return (ran == 0) ? random.nextInt(length) : ran;
        }

        private static int generateRandomInt(int min, int max) {
            Random rand = new Random();
            boolean flag = rand.nextBoolean();
            if (flag) {
                return rand.nextInt((max - min) + 1) + min;
            } else {
                return (rand.nextInt((max - min) + 1) + min) * (-1);
            }
        }

        private static class Captcha {
            private String captchaCode;
            private Bitmap bitmap;

            Captcha(String captchaCode, Bitmap bitmap) {
                this.captchaCode = captchaCode;
                this.bitmap = bitmap;
            }

            String getCaptchaCode() {
                return captchaCode;
            }

            public void setCaptchaCode(String captchaCode) {
                this.captchaCode = captchaCode;
            }

            Bitmap getBitmap() {
                return bitmap;
            }

            public void setBitmap(Bitmap bitmap) {
                this.bitmap = bitmap;
            }
        }

    }

    /**
     * 匹配验证码
     *
     * @param inputCode     用户提交的验证码
     * @param isIgnoreUpper 是否忽略大小写
     * @return
     */
    public boolean matchCode(String inputCode, boolean isIgnoreUpper) {
        if (isIgnoreUpper) {
            return TextUtils.equals(getCaptchaCode().toLowerCase(), inputCode.toLowerCase());
        } else {
            return TextUtils.equals(getCaptchaCode(), inputCode);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!isRedraw) {
            reDraw();
            isRedraw = true;
        }

    }

    /**
     * Returns the generated captcha bitmap image
     *
     * @return Generated Bitmap
     */
    public Bitmap getCaptchaBitmap() {
        return generatedCaptcha.getBitmap();
    }

    /**
     * Returns the generated captcha code
     *
     * @return captcha string
     */
    public String getCaptchaCode() {
        return generatedCaptcha.getCaptchaCode();
    }


    /**
     * Sets the type of captcha need to generate.Default value is CaptchaGenerator.NUMBER
     *
     * @param type Type of the captcha
     *             CaptchaGenerator.NUMBER - Generates a captcha with only numbers
     *             CaptchaGenerator.ALPHABETS - Generates a captcha with only letter
     *             CaptchaGenerator.BOTH - Generates a captcha with both numbers and letter
     */
    public void setCaptchaType(int type) {
        captchaType = type;
    }

    /**
     * Sets the desired length of captcha need to generate
     *
     * @param length length of captcha
     */
    public void setCaptchaLength(int length) {
        captchaLength = length;
    }

    /**
     * Method to set Background dots
     *
     * @param isNeeded pass true if dots needed false otherwise
     */
    public void setIsDotNeeded(boolean isNeeded) {
        isDot = isNeeded;
    }
}
