package interfaces

import annotations.SimpleAnnotation
import groovy.transform.NotYetImplemented

import javax.annotation.Resource
import javax.xml.bind.annotation.XmlID
import javax.xml.bind.annotation.XmlRootElement
@Resource
@XmlRootElement
@SimpleAnnotation(n = "SimpleAnnotationName", v= "42")
interface AnnotationInterface {
    @XmlID
    def field

    @NotYetImplemented
    def method (
//            @NotNull
            def arg)
}
