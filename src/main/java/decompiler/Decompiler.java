package decompiler;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafa on 22.02.2016.
 */
public class Decompiler {

    private String decompileInterface (Class<?> interafce) {
        StringBuilder builder = new StringBuilder();

        String packages = getPackage(interafce);
        builder.append(packages);
        String  name = getName(interafce);
        builder.append(name).append(" {\n");
        String variables = getVariables(interafce);
        builder.append(variables);
        String methods = getMethods(interafce);
        builder.append(methods);
        builder.append("}");

        return builder.toString();
    }

    private String getMethods(Class<?> clazz) {
        StringBuilder methods = new StringBuilder();
        return methods.toString();
    }

    private String getVariables(Class<?> clazz) {
        StringBuilder fields = new StringBuilder();
        return fields.toString();

    }

    private String getName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    private String getPackage(Class<?> clazz) {
        StringBuilder classSrt = new StringBuilder();
        String packageName = null;
        if (clazz.getPackage() != null) {
            packageName = clazz.getPackage().getName();
        } else {
            if (!Modifier.isInterface(clazz.getModifiers())
                    && !Modifier.isAbstract(clazz.getModifiers())) {
                throw new RuntimeException("lol2");
            }
        }

        if (packageName != null && !packageName.startsWith("java.") && !packageName.startsWith("javax.")) {
            classSrt.append("package").append(' ').append(packageName).append(";\n\n");
        }

        return classSrt.toString();
    }

    public List<String> decompile(List<Class<?>> classes) {
        List<String> strings = new ArrayList<>();
        return strings;
     }

    public String decompile(Class<?> clazz) {

        if (clazz.isInterface()) {
            return decompileInterface(clazz);
        }
        return  null;
    }
}
