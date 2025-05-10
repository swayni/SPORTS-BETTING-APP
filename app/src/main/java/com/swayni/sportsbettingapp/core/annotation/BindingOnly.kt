package com.swayni.sportsbettingapp.core.annotation

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
@DslMarker
@Retention(AnnotationRetention.BINARY)
annotation class BindingOnly