# BoxShadowLayout [中文](./README_ZN.md)
BoxShadowLayout like box-shadow in web and enable set radius like cardView in android. Shadow outside of View bounds.
Support Version >= Android 21
## Shadow Properties
attr|method|css properties|desc
--|--|--|--
box_shadowOffsetVertical | setShadowVerticalOffset() | h-shadow | Required. The horizontal offset of the shadow. A positive value puts the shadow on the right side of the box, a negative value puts the shadow on the left side of the box|
box_shadowOffsetHorizontal | setShadowHorizontalOffset() | v-shadow|Required. The vertical offset of the shadow. A positive value puts the shadow below the box, a negative value puts the shadow above the box|
box_shadowBlur | setShadowBlur() | blur | Optional. The blur radius. The higher the number, the more blurred the shadow will be
box_shadowSpread| setShadowSpread() | spread | Optional. The spread radius. A positive value increases the size of the shadow, a negative value decreases the size of the shadow
box_shadowColor | setShadowColor |color | Optional. The color of the shadow. The default value is the text color. Look at CSS Color Values for a complete list of possible color values. 
box_shadowInset | setShadowInset | inset | Optional. Changes the shadow from an outer shadow (outset) to an inner shadow

## Radius Properties
radius attr | method |desc
--|--|--
box_radius | setBoxRadius() | set round radius
box_radiusTopLeft|setBoxRadius()| ..
box_radiusTopRight|setBoxRadius()| ..
box_radiusBottomLeft|setBoxRadius()| ..
box_radiusBottomRight|setBoxRadius()| ..

## Sample xml
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

## Design sketch
<img src="./box_shadow_sample01.jpg"  width="80%" height="80%"/>

## Compatibility
Working with min version is android 21. Same effect on all device.

## Limitation
Don't use blur api in animation. Low sdk version device use bitmap draw blur which consume large memory.