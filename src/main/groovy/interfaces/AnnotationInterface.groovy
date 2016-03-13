package interfaces

import annotations.SimpleAnnotation
import com.sun.istack.internal.NotNull
import groovy.transform.NotYetImplemented

import javax.annotation.Resource
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlRootElement

@Resource
@XmlRootElement
@SimpleAnnotation(name = "SimpleAnnotationName")
interface AnnotationInterface {
    @XmlID
    def field

    @NotYetImplemented
    def method (@NotNull def arg)
}
