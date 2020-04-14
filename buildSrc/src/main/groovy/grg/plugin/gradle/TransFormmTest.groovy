import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtField
import javassist.bytecode.SignatureAttribute
import javassist.bytecode.stackmap.TypeData
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

import java.io.FilenameFilter
import java.lang.reflect.Modifier

class TransFormmTest extends Transform {


    @Override
    String getName() {
        return "grgTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {

        def input = transformInvocation.inputs
        def output = transformInvocation.outputProvider

        input.each {

            it.jarInputs.each {

                def target = output.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.JAR
                )


                FileUtils.copyFile(it.file, target)

            }

            it.directoryInputs.each {


                /**
                 * =================androidx
                 * =================me
                 * =================com
                 * =================META-INF
                 * =================com
                 */
                File file = buildConfigFile(it.file)
                if (file != null) {
                    fix(file)
                }

                def target = output.getContentLocation(
                        it.name,
                        it.contentTypes,
                        it.scopes,
                        Format.DIRECTORY
                )

                FileUtils.copyDirectory(it.file, target)
            }

        }

    }

    //classes/com/example/myapplication/BuildConfig.class

    private File buildConfigFile(File file) {
        File f = new File(file.absolutePath + "/com/example/myapplication/BuildConfig.class")
        if (f.exists()) {
            return f
        }
        return null
    }

    private void fix(File file) {

        FileInputStream fis = new FileInputStream(file)

        ClassPool pool = ClassPool.getDefault()

        def ctClass = pool.makeClass(fis, false)
        if (ctClass.isFrozen()) {
            ctClass.defrost()
        }

        CtField testField = new CtField(
                pool.get("java.lang.String"),
                "JAVA_ASSIST_TEST",
                ctClass)

//        CtField ct1= CtField.make("""
//public static final java.lang.String QWER = 'sdgssdgsdg'
//""",ctClass)

//        ct1.
//        ctClass.addField(ct1)

//        TypeVariable

//        ClassSignature


//        testField.setGenericSignature(new SignatureAttribute.TypeVariable().encode())

        testField.modifiers = Modifier.PUBLIC
//        testField.

        ctClass.addField(testField)

        fis.close()

        FileOutputStream fos = new FileOutputStream(file)
        fos.write(ctClass.toBytecode())
        fos.close()

//        FileInputStream fis = new FileInputStream(file)
//        ClassWriter classWriter = new ClassWriter(new ClassReader(fis), 0)
//
//        classWriter.visit(Opcodes.V1_8,
//                Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_SUPER,
//                "com/example/myapplication/BuildConfig",
//                null,
//                "java/lang/Object",
//                null);
//
//        classWriter.metaPropertyValues.each {
//            println it.name
//            println it.value
//        }
//
//        FieldVisitor fieldVisitor = classWriter.visitField(
//                Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL | Opcodes.ACC_STATIC,
//                "WTF_VALUE",
//                "Ljava/lang/String;",
//                null,
//                "WTF_VALUE")
//
//        fieldVisitor.visitEnd()
//
//        classWriter.visitEnd()
//
//        fis.close()
//
//        FileOutputStream fos = new FileOutputStream(file)
//        fos.write(classWriter.toByteArray())
//        fos.close()


//        ClassWriter classWriter = new ClassWriter(0);
//        FieldVisitor fieldVisitor;
//        MethodVisitor methodVisitor;
//        AnnotationVisitor annotationVisitor0;
//
//        classWriter.visit(V1_8, ACC_PUBLIC | ACC_FINAL | ACC_SUPER, "com/example/myapplication/BuildConfig", null, "java/lang/Object", null);
//
//        classWriter.visitSource("BuildConfig.java", null);

//        {
//            fieldVisitor = classWriter.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "DEBUG", "Z", null, null);
//            fieldVisitor.visitEnd();
//        }
//        {
//            fieldVisitor = classWriter.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "APPLICATION_ID", "Ljava/lang/String;", null, "com.example.myapplication");
//            fieldVisitor.visitEnd();
//        }
//        {
//            fieldVisitor = classWriter.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "BUILD_TYPE", "Ljava/lang/String;", null, "debug");
//            fieldVisitor.visitEnd();
//        }
//        {
//            fieldVisitor = classWriter.visitField(ACC_PUBLIC | ACC_FINAL | ACC_STATIC, "FLAVOR", "Ljava/lang/String;", null, "");
//            fieldVisitor.visitEnd();
//        }
    }

}
