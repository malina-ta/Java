import java.util.ArrayList;

public class Student {
    private final String name;
    private final ArrayList<Subject> subjects;

    public Student(String name){
        this.name = name;
        this.subjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addSubjects(Subject subject) {
        this.subjects.add(subject);
    }

    public String toString()
    {
        return name;
    }
}
