package by.itacademy.user.aspect;

import by.itacademy.user.core.enums.ActionForAudit;
import by.itacademy.user.core.enums.EntityForAudit;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditor {
    ActionForAudit action();
    EntityForAudit entity();
}
