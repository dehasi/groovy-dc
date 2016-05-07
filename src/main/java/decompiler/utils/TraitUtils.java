package decompiler.utils;


import decompiler.holders.ClassHolder;
import decompiler.holders.FieldHolder;
import decompiler.holders.MethodHolder;

import static decompiler.utils.ParserUtils.EMPTY_STRING;
import static decompiler.utils.ParserUtils.getNameWithoutPackage;

public class TraitUtils {

    public static final String PUBLIC = "public ";

    public static ClassHolder processTrait(ClassHolder trait ){
        extractFields(trait);
        extractMethods(trait);
        filerMdifiers(trait);


        return trait;
    }

    private static void extractMethods(ClassHolder trait) {
        ClassHolder helper = getHelper(trait, false);
        for (MethodHolder m: helper.methods) {
            if (!isSystem(m.name) && !trait.contains(m)) {
                trait.methods.add(m);
            }

        }
    }

    private static void filerMdifiers(ClassHolder trait) {
        for (MethodHolder m: trait.methods) {
            int start = m.parsedModifiers.indexOf(PUBLIC);
            if (start != -1)
            m.parsedModifiers.replace(start,PUBLIC.length(), EMPTY_STRING );

        }
    }

    private static void extractFields(ClassHolder trait) {
        ClassHolder fieldHelper = getHelper(trait, true);
        if (fieldHelper == null) return;
        for (FieldHolder f : fieldHelper.fields) {
            f.field.replace(f.field.indexOf("$"),f.field.indexOf("__")+2, "");
            trait.fields.add(f);
        }

    }

    private static String clearName(String fieldName, String traitName) {
        traitName = getNameWithoutPackage(traitName);
        fieldName = fieldName.substring(fieldName.indexOf(traitName) + traitName.length()+2);
        return fieldName;
    }

    private static ClassHolder getHelper(ClassHolder trait, boolean isField) {
        ClassHolder fieldHelper = null;
        String fh = "$Trait$FieldHelper";
        String h = "$Trait$Helper";
        String targetName = getNameWithoutPackage(trait.header.name) +(isField?fh:h) ;
        for (ClassHolder ch : trait.inner) {
            if (ch.header.name.contains(targetName)){
                fieldHelper = ch;
                break;
            }

        }
        return fieldHelper;
    }



    private static boolean isSystem(String name) {
        final String[] systemMethods = {
                "propertyMissing", "methodMissing",
                "<init>", "<cinit>", "<clinit>",
                "getMetaClass", "setMetaClass", "invokeMethod",
                "getProperty", "setProperty"
        };
        for (String sm : systemMethods)
            if (sm.equals(name)) return true;
        return name.contains("$");
    }


}
