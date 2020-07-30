import java.util.HashSet;

public class Student {
    private final String name;
    private final HashSet<Subject> subjects;

    public Student(String name){
        this.name = name;
        this.subjects = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public HashSet<Subject> getSubjects() {
        return subjects;
    }

    public void addSubjects(Subject subject) {
        this.subjects.add(subject);
    }

    public void deleteSubject(Subject subject){
        this.subjects.remove(subject);
    }

    public String toString()
    {
        return name;
    }
}
