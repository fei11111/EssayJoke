package com.fei.framelibrary.skin;

/**
 * @ClassName: SkinResource
 * @Description: 替换资源
 * @Author: Fei
 * @CreateDate: 2020/11/16 15:28
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/16 15:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SkinResource {

    /*
    *    try {
            Resources superRes = getResources();
            //AssetManager只能反射
            AssetManager assetManager = AssetManager.class.newInstance();
            //addAssetPath不可见，反射获取
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            //皮肤文件
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "skin.skin");
            if (!file.exists()) {
                LogUtils.i(TAG, "皮肤文件不存在");
                return;
            }
            if (addAssetPath != null) {
                //加载皮肤
                addAssetPath.invoke(assetManager, file.getAbsolutePath());
                //创建皮肤Resources
                Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                // 通过资源名称,类型，包名获取Id
                int id = resources.getIdentifier("main_bg", "drawable", "com.fei.skin");
                Drawable drawable = resources.getDrawable(id);
                ImageView imageView = findViewById(R.id.iv_bg);
                imageView.setImageDrawable(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    * */
}
