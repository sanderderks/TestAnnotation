package me.sderks.annotations

import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

// who can use this annotation?
@Target(AnnotationTarget.FUNCTION)
// does this annotation need to exist in the final binary code?
@Retention(AnnotationRetention.SOURCE)
annotation class BasicAnnotation
{

    companion object
    {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}