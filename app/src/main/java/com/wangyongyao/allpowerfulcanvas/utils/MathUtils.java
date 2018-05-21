package com.wangyongyao.allpowerfulcanvas.utils;

import android.content.Context;
import android.util.TypedValue;

import java.util.Random;

/**
 * @author wangyao
 * @package com.wangyongyao.allpowerfulcanvas.utils
 * @describe TODO
 * @date 2018/5/21
 */

public class MathUtils {

    public static int getRandomNum( int min , int max){
        Random random = new Random();
        return random.nextInt( max - min + 1) + min;
    }

    public static int dpToPx(Context context,int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
