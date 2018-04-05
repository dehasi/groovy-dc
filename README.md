# Groovy decompiler

Decompiles only signature of a groovy class.

__Dislaimer: Is's not a complete decompiler that can be used in a real project.__

I.e. code like this
```groovy
package traits

trait OneFuncTrait {
  int inc (int t) {
    ++t
  }
}
```

will be decompiled to this
```groovy
package traits

trait OneFuncTrait {
  int inc (int var0) {
    throw new Exception("I can't parse body");
  }
}
```

It also expects classes to be on the file system and not in a jar.
There is no runner right now. To use code see the example below
```
Decompiler decompiler = new Decompiler();
Loader loader = new Loader();
final String path = TESTS_DIRECTORY + packageName + File.separator + name + ".class";

ClassReader classReader = loader.loadFromFileSystem(path);
String content = decompiler.decompile(classReader).toString();

System.out.println(content);
```