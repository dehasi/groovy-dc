package decompiler;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class Decompiler {

    private String decompileInterface(Class<?> interafce) {
        StringBuilder builder = new StringBuilder();

        StringBuilder packages = getPackage(interafce);
        builder.append(packages);

        StringBuilder name = getName(interafce);
        builder.append("interface ").append(name).append(" {\n");

        StringBuilder variables = getFields(interafce);
        builder.append(variables).append('\n');

        String methods = getInterfaceMethods(interafce);
        builder.append(methods);

        builder.append("}");
        return builder.toString();
    }

    private String getInterfaceMethods(Class<?> clazz) {
        StringBuilder methods = new StringBuilder();
        List<Method> methodsList = new ArrayList<>();
        for (Class<?> clz = clazz; clz != null; clz = clz.getSuperclass()) {
            Collections.addAll(methodsList, clz.getMethods());
            Collections.addAll(methodsList, clz.getDeclaredMethods());
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
        return method;
    }

    private StringBuilder createBracketsInside(Executable executable) {
        StringBuilder inside = new StringBuilder();
        inside.append('(');

        Class<?>[] xType = executable.getParameterTypes();
        Type[] gxType = executable.getGenericParameterTypes();
        int i = 0;
        for (; i < xType.length; i++) {
//TODO: what about modifiers?
//            if (Modifier.isStatic(xType[i].getModifiers())) {
//                inside.append(' ').append("static").append(' ');
//            }
//
//            if (Modifier.isFinal(xType[i].getModifiers())) {
//                inside.append(' ').append("final").append(' ');
//            }


            if (gxType[i] != Object.class) {
                inside.append(gxType[i])
                        .append(' ')
                        .append(gxType[i].toString().toLowerCase())
                        .append(i)
                        .append(',').append(' ');
            } else {
                if (xType[i] == Object.class) {
                    inside.append("def var").append(i);
                } else {
                    inside.append(xType[i].getCanonicalName())
                            .append(' ')
                            .append(xType[i].getSimpleName().replace('[', '_').replace(']', '_').toLowerCase())
                            .append(i);
                }
                inside.append(',').append(' ');
            }
        }
        if (i != 0) {
            inside.setLength(inside.length() - 2);
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

        name.append('\t');
        if (method.getTypeParameters().length > 0) {
            name.append("public ");
            name.append(createGeneticString(method.getTypeParameters())).append(' ');
        }
        if (method.getGenericReturnType() != Object.class) {
            name.append(method.getGenericReturnType());

        } else {
            if (method.getReturnType() == Object.class) {
                name.append("def");
            } else {
                name.append(method.getReturnType().getCanonicalName());
            }
        }
        name.append(' ').append(method.getName());

        return name;
    }


    private StringBuilder createGeneticString(TypeVariable<?>[] typeVariables) {
        StringBuilder params = new StringBuilder();
        if (typeVariables.length > 0) {
            params.append("<");
            for (TypeVariable<?> tv : typeVariables) {
                params.append(tv.getName()).append(", ");
            }
            params.setLength(params.length() - 2);
            params.append(">");
        }
        return params;
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

    private StringBuilder getFields(Class<?> clazz) {
        StringBuilder fields = new StringBuilder();
        for (Field field : clazz.getFields()) {
            fields.append('\t');
            if (!clazz.isInterface()) {
                fields.append(Modifier.toString(field.getModifiers())).append(" ");
            }
            if (field.getGenericType() != Object.class) {
                fields.append(field.getGenericType().getTypeName()).append(" ");
            } else {
                if (field.getType() == Object.class) {
                    fields.append("def").append(" ");
                } else {
                    fields.append(field.getType().getName()).append(" ");
                }
            }
            fields.append(field.getName()).append("\n");

        }
        return fields;

    }

    private StringBuilder getName(Class<?> clazz) {
        StringBuilder className = new StringBuilder(clazz.getSimpleName());
        className.append(createGeneticString(clazz.getTypeParameters()));
        return className;
    }

    private StringBuilder getPackage(Class<?> clazz) {
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

        return classSrt;
    }

    public List<String> decompile(List<Class<?>> classes) {
        return classes.stream().map(this::decompile).collect(Collectors.toList());
    }

    public String decompile(Class<?> clazz) {

        if (clazz.isInterface()) {
            return decompileInterface(clazz);
        }
        return null;
    }
}
