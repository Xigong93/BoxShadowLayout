Draw shadow in android
- Paint.setShadowLayer()
    Works on all sdk version ,but only support LAYER_TYPE_SOFTWARE,and shadow color must have alpha.
- BlurMaskFilter
    Works on devices which great than  or equals on android 8.0 with hardware acceleration. Or Works on all sdk version without hardware acceleration.
- RenderScript Bitmap
    Works on all sdk version.