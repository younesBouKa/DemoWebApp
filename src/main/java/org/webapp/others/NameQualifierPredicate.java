package org.webapp.others;


import org.injection.annotations.QualifierConfig;
import org.injection.core.qualifiers.QualifierPredicate;

import java.lang.annotation.Annotation;

@QualifierConfig(qualifierAnnotation = NameQualifier.class)
public class NameQualifierPredicate implements QualifierPredicate {
    @Override
    public boolean accept(Annotation annotation, Object beanSource) {
        NameQualifier nameQualifier = (NameQualifier) annotation;
        if(beanSource instanceof Class){
            return ((Class)beanSource).getCanonicalName().equals(nameQualifier.name());
        }
        return false;
    }
}
