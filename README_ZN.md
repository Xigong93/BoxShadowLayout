# BoxShadowLayout
在安卓上实现了css3的box-shadow属性
box-shadow属性参考: https://www.runoob.com/cssref/css3-pr-box-shadow.html

## 特性
- Shadow不占据View的空间，设置了Parent.clipChildren=false
- 完美实现了css3的box-shadow属性

## 属性
h-shadow	必需的。水平阴影的位置。允许负值
v-shadow	必需的。垂直阴影的位置。允许负值
blur	可选。模糊距离
spread	可选。阴影的大小
color	可选。阴影的颜色。在CSS颜色值寻找颜色值的完整列表
inset	可选。从外层的阴影（开始时）改变阴影内侧阴影
clipRadius  可选。类似cardView,会把子View裁剪为圆角


参考文章:
- 阴影组件化，解决安卓不统一问题 https://zhuanlan.zhihu.com/p/130858529