import java.util.ArrayList;

public class UniversityStorage {
    private final ArrayList<Student> storageStudents;
    private final ArrayList<Subject> storageSubjects;
    public UniversityStorage()
    {
        storageStudents = new ArrayList<>();
        storageSubjects = new ArrayList<>();
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

    public ArrayList<Subject> getSubjectFromStudent (Student student){
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

    public ArrayList<Subject> getSubjects(){
        return storageSubjects;
    }
}
