package ru.kosterror.testsforge.securitystarter.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleExpressions {
    public static final String TEACHER_OR_STUDENT = "hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER')";
    public static final String TEACHER = "hasRole('ROLE_TEACHER')";
    public static final String STUDENT = "hasRole('ROLE_STUDENT')";
    public static final String SERVICE = "hasService('ROLE_SERVICE')";
    public static final String TEACHER_OR_STUDENT_OR_SERVICE = "hasAnyRole('ROLE_STUDENT', 'ROLE_TEACHER', 'ROLE_SERVICE')";
}
