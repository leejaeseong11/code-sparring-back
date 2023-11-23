package com.trianglechoke.codesparring.code.control;

import javax.tools.JavaFileObject;

public abstract class SimpleJavaFileObject {

    public SimpleJavaFileObject(Object o, JavaFileObject.Kind kind) {
    }

    public abstract CharSequence getCharContent(boolean ignoreEncodingErrors);
}
