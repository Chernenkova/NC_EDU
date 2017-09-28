package Elena.Chernenkova;


import Elena.Chernenkova.controller.DepartmentController;
import Elena.Chernenkova.controller.StudentsController;
import Elena.Chernenkova.entity.Department;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 123 on 12.09.2017.
 */
public class Service {

    private static DepartmentController departmentController;
    private static StudentsController studentsController;

    public static Set<Department> departments = new HashSet<>();

    public static String findJson(String parameter, String s) {
        char[] a = s.toCharArray();
        int i = s.indexOf(parameter) + parameter.length() + 3;
        StringBuilder result = new StringBuilder();
        while (a[i] != '\"') {
            result.append(a[i++]);
        }
        return result.toString();
    }

    public static DepartmentController getDepartmentController() {
        return departmentController;
    }

    public static void setDepartmentController(DepartmentController departmentController) {
        Service.departmentController = departmentController;
    }

    public static StudentsController getStudentsController() {
        return studentsController;
    }

    public static void setStudentsController(StudentsController studentsController) {
        Service.studentsController = studentsController;
    }
}

