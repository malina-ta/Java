import java.util.ArrayList;
import java.util.HashSet;

public class UniversityStorage {
    private final ArrayList<Student> storageStudents;
    private final HashSet<Subject> storageSubjects;
    public UniversityStorage()
    {
        storageStudents = new ArrayList<>();
        storageSubjects = new HashSet<>();
    }

    public void addStudent (Student student){
        storageStudents.add(student);
    }

    public int deleteStudent (Student student){
        if(storageStudents.contains(student)){
            storageStudents.remove(student);
            return 1;
        }
        return -1;
    }

    public void addSubject (Subject subject){
        storageSubjects.add(subject);
    }

    public int deleteSubject(Subject subject){
        if(storageSubjects.contains(subject)){
            storageSubjects.remove(subject);
            for (Student student : storageStudents){
                if(student.getSubjects().contains(subject)) {
                    student.deleteSubject(subject);
                }
            }
            return 1;
        }
        return -1;
    }

    public int editSubject(Subject subject, String name, String description, Integer hours){
        if(storageSubjects.contains(subject)){
            subject.setName(name);
            subject.setDescription(description);
            subject.setHours(hours);
            return 1;
        }
        return -1;
    }

    public HashSet<Subject> getSubjectFromStudent (Student student){
        if(storageStudents.contains(student)){
            if(student.getSubjects().isEmpty()){
                return null;
            }
            return student.getSubjects();
        }
        return null;
    }

    public ArrayList<Student> getStudentsFromSubject (Subject subject){
        if(storageSubjects.contains(subject)){
            ArrayList<Student> students = new ArrayList<>();
            for(Student student : storageStudents){
                if(student.getSubjects().contains(subject)){
                    students.add(student);
                }
            }
            return students;
        }
        return null;
    }

    public ArrayList<Student> getStudents (){
        return storageStudents;
    }

    public HashSet<Subject> getSubjects(){
        return storageSubjects;
    }
}
