package decompiler;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rafa on 22.02.2016.
 */
public class Decompiler {

    private String decompileInterface(Class<?> interafce) {
        StringBuilder builder = new StringBuilder();

        String packages = getPackage(interafce);
        builder.append(packages);
        String name = getName(interafce);
        builder.append(name).append(" {\n");
        String variables = getFields(interafce);
        builder.append(variables);
        String methods = getInterfaceMethods(interafce);
        builder.append(methods);
        builder.append("}");

        return builder.toString();
    }

    private String getInterfaceMethods(Class<?> clazz) {
        StringBuilder methods = new StringBuilder();
        List<Method> methodsList = new ArrayList<>();
        for (Class<?> clz = clazz; clz != null; clz = clz.getSuperclass()) {
            for (Method m : clz.getMethods()) {
                if (Modifier.isAbstract(m.getModifiers())) {
                    methodsList.add(m);
                }
            }
            for (Method m : clz.getDeclaredMethods()) {
                if (Modifier.isAbstract(m.getModifiers())) {
                    methodsList.add(m);
                }
            }
        }
        Set<Method> yet = new HashSet<>();
        methodsList.stream().filter(method -> !isYet(yet, method)).forEach(method -> {
            methods.append(createMetod(method)).append("\n");
            yet.add(method);
        });
        return methods.toString();
    }

    private StringBuilder createMetod(Method m) {
        StringBuilder method = createMethodName(m);
        method.append(createBracketsInside(m));
        method.append(createException(m));
//        method.append(createMethodBody(m));
        return method;
    }

    private StringBuilder createBracketsInside(Executable executable) {
        StringBuilder inside = new StringBuilder();
        inside.append('(');

        int i = 0;
        for (Class<?> p : executable.getParameterTypes()) {
            if (Modifier.isStatic(p.getModifiers())) {
                inside.append(' ').append("static").append(' ');
            }

            if (Modifier.isFinal(p.getModifiers())) {
                inside.append(' ').append("final").append(' ');
            }

            inside.append(p.getCanonicalName())
                    .append(' ')
                    .append(p.getSimpleName().replace('[', '_').replace(']', '_'))
                    .append(i)
                    .append(',');

            ++i;
        }

        if (i != 0) {
            inside.setLength(inside.length() - 1);
        }
        inside.append(')');
        return inside;
    }

    private StringBuilder createException(Executable executable) {
        StringBuilder exception = new StringBuilder("");
        for (Class<?> e : executable.getExceptionTypes()) {
            exception.append(e.getCanonicalName());
            exception.append(',').append(' ');
        }
        if (exception.length() > 0) {
            exception.setLength(exception.length() - 2);
            exception.insert(0, " throws ");
        }
        return exception;
    }
    private StringBuilder createMethodName(Method method) {
        StringBuilder name = new StringBuilder();

        name.append('\t')
                .append(Modifier.toString(method.getModifiers() & ~Modifier.ABSTRACT))
                .append(' ')
                .append(method.getReturnType().getCanonicalName())
                .append(' ')
                .append(method.getName());
        return name;
    }

    private StringBuilder createMethodBody(Method m) {
        StringBuilder body = new StringBuilder();
        body.append(" { \n\t\t").append(' ');
        body.append("throw  new UnsupportedOperationException();");
        body.append("\n\t}\n");
        return body;
    }
    boolean isYet(Set<Method> yet, Method method) {
        for (Method m : yet) {
            if (m.getName().equals(method.getName())
                    && m.getReturnType().equals(method.getReturnType())
                    && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())
                    ) {
                return true;
            }
        }
        return false;
    }
    private String getFields(Class<?> clazz) {
        StringBuilder fields = new StringBuilder();
        for (Field field : clazz.getFields()) {
            if (!clazz.isInterface()) {
                fields.append(Modifier.toString(field.getModifiers())).append(" ");
            }

                fields.append(field.getType().getName()).append(" ");
                fields.append(field.getName()).append("\n");

        }
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
        return null;
    }
}
