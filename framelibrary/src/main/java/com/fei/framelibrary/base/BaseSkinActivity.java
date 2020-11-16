package com.fei.framelibrary.base;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.fei.baselibrary.base.BaseActivity;
import com.fei.framelibrary.skin.SkinAttr;
import com.fei.framelibrary.skin.SkinManager;
import com.fei.framelibrary.skin.SkinView;
import com.fei.framelibrary.skin.callback.SkinCallback;
import com.fei.framelibrary.skin.support.SkinCompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.view.LayoutInflaterCompat;

import static androidx.appcompat.widget.VectorEnabledTintResources.MAX_SDK_WHERE_REQUIRED;


public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflater.Factory, SkinCallback {

    private static final String TAG = "BaseSkinActivity";
    //源码的createView方法为final无法调用，直接将源码拷过来，并修改访问修饰符
    private SkinCompatViewInflater mAppCompatViewInflater;
    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //拦截绘制过程，获取界面所有View
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);//先于系统拦截
    }

    /**
     * 拦截，将源码拷贝过来
     */
    @Override
    public final View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return createView(parent, name, context, attrs);
    }

    /**
     * 将拦截的view，换成皮肤view
     */
    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            TypedArray a = obtainStyledAttributes(R.styleable.AppCompatTheme);
            String viewInflaterClassName =
                    a.getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (viewInflaterClassName == null) {
                // Set to null (the default in all AppCompat themes). Create the base inflater
                // (no reflection)
                mAppCompatViewInflater = new SkinCompatViewInflater();
            } else {
                try {
                    Class<?> viewInflaterClass = Class.forName(viewInflaterClassName);
                    mAppCompatViewInflater =
                            (SkinCompatViewInflater) viewInflaterClass.getDeclaredConstructor()
                                    .newInstance();
                } catch (Throwable t) {
                    Log.i(TAG, "Failed to instantiate custom view inflater "
                            + viewInflaterClassName + ". Falling back to default.", t);
                    mAppCompatViewInflater = new SkinCompatViewInflater();
                }
            }
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : true;
        }

        View view = mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                false && Build.VERSION.SDK_INT <= MAX_SDK_WHERE_REQUIRED /* Only tint wrap the context if enabled */
        );

        //获取满足要求的属性
        List<SkinAttr> array = new ArrayList<>();
        if (view != null) {
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                //1.获取属性名、属性值,属性值是@2123123
                String attributeName = attrs.getAttributeName(i);
                String attributeValue = attrs.getAttributeValue(i);
                //2.必须是@开头的属性值才能换肤，根据属性值@2123123获取资源名getResourceEntryName，资源类型getResourceTypeName
                if (attributeValue.startsWith("@")) {
                    //2.1获取所有@开头的属性
                    array.add(createSkinAttr(attributeValue));
                }
            }
            //3.创建SkinView
            if (array.size() > 0) {
                SkinView skinView = new SkinView(view, array);
                //4.存放入SkinManager管理
                SkinManager.getInstance().register(this, skinView);
            }
        }


        return view;
    }

    /**
     * 生成自定义属性
     * 必须是@开头的属性值才能换肤，根据属性值@2123123获取资源名getResourceEntryName，资源类型getResourceTypeName
     */
    private SkinAttr createSkinAttr(String attributeValue) {
        //获取该app资源类
        Resources resources = getResources();
        //通过资源类获取资源名，去掉@符号
        int index = Integer.parseInt(attributeValue.substring(1));
        //获取资源名
        String resourceEntryName = resources.getResourceEntryName(index);
        //获取资源类型
        String resourceTypeName = resources.getResourceTypeName(index);
        return new SkinAttr(resourceEntryName, resourceTypeName);
    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void callback(View view, SkinAttr attr, String packageName) {

    }
}
