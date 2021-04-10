# BoxShadowLayout
实现了类似css3中的box-shadow 属性，并且支持裁减圆角像CardView一样。阴影不占据View的空间。以实现类似ios的阴影效果

## Shadow Properties
xml 属性|方法|对应的css中box-shadow属性|描述
--|--|--|--
shadowOffsetVertical | setShadowVerticalOffset() | h-shadow |水平阴影的位置。允许负值
shadowOffsetHorizontal | setShadowHorizontalOffset() | v-shadow|垂直阴影的位置。允许负值
shadowBlur | setShadowBlur() | blur | 模糊距离
shadowSpread| setShadowSpread() | spread | 阴影的大小
shadowColor | setShadowColor |color | 阴影的颜色
shadowInset | setShadowInset | inset | 从外层的阴影（开始时）改变阴影内侧阴影

## Radius Properties
radius attr | method |desc
--|--|--
boxRadius | setBoxRadius() | 设置4角圆角大小
boxRadiusTopLeft|setBoxRadius()| 设置左上圆角大小
boxRadiusTopRight|setBoxRadius()| 设置右上圆角大小
boxRadiusBottomLeft|setBoxRadius()| 设置左下圆角大小
boxRadiusBottomRight|setBoxRadius()| 设置右下圆角大小

## 实例 xml
```xml
<pokercc.android.boxshadowlayout.BoxShadowLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:layout_marginStart="60dp"
    android:layout_marginEnd="60dp"
    app:box_radius="20dp"
    app:box_radiusBottomLeft="8dp"
    app:box_radiusBottomRight="8dp"
    app:box_shadowBlur="8dp"
    app:box_shadowColor="#f00"
    app:box_shadowInset="false"
    app:box_shadowOffsetHorizontal="5dp"
    app:box_shadowOffsetVertical="5dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scaleType="centerCrop"
        android:src="@drawable/unsplash_01"
        tools:ignore="ContentDescription" />
</pokercc.android.boxshadowlayout.BoxShadowLayout>
```

## 效果图
<img src="./box_shadow_sample01.jpg"  width="80%" height="80%"/>

## 兼容性
支持安卓系统版本Android 21及以上,效果完全一致
在android 28 及以上，使用GPU渲染高斯模糊，低版本使用Bitmap,配合离屏Canvas绘制高斯模糊

## 限制
不要在动画中使用模糊api。低版本设备使用位图绘制模糊，消耗大内存。

## 参考文章
- https://xuyisheng.top/mdc-shape/
-