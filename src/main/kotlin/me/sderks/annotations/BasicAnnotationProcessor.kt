package me.sderks.annotations

import me.sderks.annotations.BasicAnnotation.Companion.KAPT_KOTLIN_GENERATED_OPTION_NAME
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(KAPT_KOTLIN_GENERATED_OPTION_NAME)
@SupportedAnnotationTypes("*")
class BasicAnnotationProcessor : AbstractProcessor()
{

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean
    {
        for (element in roundEnv.getElementsAnnotatedWith(BasicAnnotation::class.java))
        {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Found an element with @BasicAnnotation!")

            val func = FunSpec.builder(element.simpleName.toString())
                .addStatement("println(\"Added this line!\")")
                .build()

            val generatedDirectory =
                processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
                    ?: return false

            val eElement = element.enclosingElement
            if(eElement.kind.isClass)
            {
                val className = eElement.simpleName.toString().replace("Kt", "")

                FileSpec.builder(processingEnv.elementUtils.getPackageOf(element).toString(), className)
                    .addFunction(func)
                    .build()
                    .writeTo(File(generatedDirectory, "$className.kt"))

                processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Processed: $className")
            }
        }
        return true
    }
}