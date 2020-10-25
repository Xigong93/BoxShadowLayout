# BoxShadowLayout [中文](./README_ZN.md)
BoxShadowLayout like box-shadow in web and enable set radius like cardView in android. Shadow outside of View bounds.

## Shadow Properties
attr|method|css properties|desc
--|--|--|--
shadowOffsetVertical | setShadowVerticalOffset() | h-shadow | Required. The horizontal offset of the shadow. A positive value puts the shadow on the right side of the box, a negative value puts the shadow on the left side of the box|
shadowOffsetHorizontal | setShadowHorizontalOffset() | v-shadow|Required. The vertical offset of the shadow. A positive value puts the shadow below the box, a negative value puts the shadow above the box|
shadowBlur | setShadowBlur() | blur | Optional. The blur radius. The higher the number, the more blurred the shadow will be
shadowSpread| setShadowSpread() | spread | Optional. The spread radius. A positive value increases the size of the shadow, a negative value decreases the size of the shadow
shadowColor | setShadowColor |color | Optional. The color of the shadow. The default value is the text color. Look at CSS Color Values for a complete list of possible color values. 
shadowInset | setShadowInset | inset | Optional. Changes the shadow from an outer shadow (outset) to an inner shadow

## Radius Properties
radius attr | method |desc
--|--|--
boxRadius | setBoxRadius() | set round radius
boxRadiusTopLeft|setBoxRadius()| ..
boxRadiusTopRight|setBoxRadius()| ..
boxRadiusBottomLeft|setBoxRadius()| ..
boxRadiusBottomRight|setBoxRadius()| ..

## Sample xml
```xml
<pokercc.android.boxshadowlayout.BoxShadowLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:layout_marginStart="60dp"
    android:layout_marginEnd="60dp"
    app:boxRadius="20dp"
    app:boxRadiusBottomLeft="8dp"
    app:boxRadiusBottomRight="8dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:shadowBlur="8dp"
    app:shadowColor="#f00"
    app:shadowInset="false"
    app:shadowOffsetHorizontal="5dp"
    app:shadowOffsetVertical="5dp">

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