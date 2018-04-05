# groovy-dc
groovy decompiler

Decompiles only signature of a groovy class.

I.e. code like this
```groovy
package traits

trait OneFuncTrait {
  int inc (int t) {
    ++t
  }
}
```

To this
```groovy
package traits

trait OneFuncTrait {
  int inc (int var0) {
    throw new Exception("I can't parse body");
  }
}
```

There is no runner right now. To use code see the example below
```
Decompiler decompiler = new Decompiler();
Loader loader = new Loader();
final String path = TESTS_DIRECTORY + packageName + File.separator + name + ".class";

ClassReader classReader = loader.loadFromFileSystem(path);
String content = decompiler.decompile(classReader).toString();

System.out.println(content);
```