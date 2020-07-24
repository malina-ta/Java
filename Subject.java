public class Subject {
    private String name;
    private String description;
    private int hours;

    public Subject(String name, String description, int hours){
        this.name = name;
        this.description = description;
        this.hours = hours;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setHours(Integer hours){
        this.hours = hours;
    }

    public String toString()
    {
        return name + " - " + description + " , часов в курсе: " + hours;
    }
}
